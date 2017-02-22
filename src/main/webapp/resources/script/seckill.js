/**
 * Created by yunfei on 2017/2/22.
 */
var seckill={
    // 封装ajax url
    URL:{
        now:function () {
            return '/seckill/time/now';
        },
        exposer: function (seckillId) {
            return '/seckill/' + seckillId + '/exposer';
        },
        execution : function (seckillId, md5) {
            return '/seckill/' + seckillId + '/' + md5 + '/execution';
        }
    },
    validatePhone: function (phone) {
        if(phone&& phone.length == 11 && !isNaN(phone)){
            return true;
        }else {
            return false;
        }
    },
    handleSeckill:function (seckillId,node) {
      //处理秒杀逻辑
        node.hide().html('<button class="btn btn-primary btn-lg" id="killBtn">开始秒杀</button>');
        $.post(seckill.URL.exposer(seckillId),{},function (result) {
            if(result && result['success']){
                var exposer = result['data'];
                if(exposer['exposed']){
                    var md5 = exposer['md5'];
                    var killUrl = seckill.URL.execution(seckillId,md5);
                    console.log("地址" + killUrl);
                    // 绑定一次点击事件
                    $('#killBtn').one('click',function () {
                        $(this).addClass('disabled');
                        // 发送请求
                        $.post(killUrl,{},function (result) {
                            if(result && result['success']){
                                var killResult = result['data'];
                                var state = killResult['state'];
                                var stateInfo = killResult['stateInfo'];
                                node.html('<span class="label label-success">' + stateInfo + '</span>')
                            }
                        });
                    });
                    node.show();
                }else {
                    var now = exposer['now'];
                    var start = exposer['start'];
                    var end = exposer['end'];
                    seckill.countdown(seckillId,now,start,end);
                }
            }else console.log("result:" + result);
        });

    },
    // 时间判断函数
    countdown: function (seckillId,nowTime,startTime,endTime) {
        var seckillBox = $('#seckill-box');
        if(nowTime > endTime){
            // 秒杀结束
            seckillBox.html('秒杀结束');
        }else if(nowTime < startTime){
            // 未开始
            var killTime = new Date(startTime + 1000);
            seckillBox.countdown(killTime,function (event) {
                var format = event.strftime('start：%D天 %H时 %M分 %S秒');
                seckillBox.html(format);
            }).on('finish.countdown',function () {
                //时间完成后回调，获取秒杀地址
                seckill.handleSeckill(seckillId,seckillBox);
            })
        }else {
            // 秒杀开始
            // seckill.handleSeckill(seckillId,seckillBox);
        }
    },
    // 封装秒杀逻辑
    detail:{
        // 详情页初始化
        init:function (params) {
            var killPhone = $.cookie('killPhone');
            var startTime = params['startTime'];
            var endTime = params['endTime'];
            var seckillId = params['seckillId'];
            if(!seckill.validatePhone(killPhone)){
                var killPhoneModal = $('#killPhoneModal');
                killPhoneModal.modal({
                    show:true,
                    backdrop:'static',//禁止位置关闭
                    keyboard:false//关闭键盘事件
                });
                $('#killPhoneBtn').click(function () {
                    var inputPhone = $('#killPhone').val();
                    if(seckill.validatePhone(inputPhone)){
                        $.cookie('killPhone',inputPhone,{expires:7,path:'/seckill'});
                        window.location.reload();
                    }else {
                        $('#killPhoneMessage').hide().html('<label class="label label-danger">手机号错误！</label>').show(300);
                    }
                });
            }

            // 计时交互
            $.get(seckill.URL.now(),{},function (result) {
                if(result && result['success']){
                    var nowTime = result['data'];
                    // 计时服务的时间判断
                    seckill.countdown(seckillId,nowTime,startTime,endTime);
                }else {
                    console.log("result:" + result);
                }
            })
        }
    }
}