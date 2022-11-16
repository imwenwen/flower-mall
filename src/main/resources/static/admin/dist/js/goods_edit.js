var editorD;

$(function () {

    function  fn(){
        var     name=$("#item1").find("option:selected").val();  //获取value值
        if(name !==null){
            version=name;
        }
    };

    //图片上传插件初始化 用于商品预览图上传
    new AjaxUpload('#uploadGoodsCoverImg', {
        action: '/admin/upload/file',
        name: 'file',
        autoSubmit: true,
        responseType: "json",
        onSubmit: function (file, extension) {
            if (!(extension && /^(jpg|jpeg|png|gif)$/.test(extension.toLowerCase()))) {
                alert('只支持jpg、png、gif格式的文件！');
                return false;
            }
        },
        onComplete: function (file, r) {
            if (r != null && r.resultCode == 200) {
                $("#goodsCoverImg").attr("src", r.data);
                $("#goodsCoverImg").attr("style", "width: 128px;height: 128px;display:block;");
                return false;
            } else {
                alert("error");
            }
        }
    });
});

$('#saveButton').click(function () {
    var goodsId = $('#goodsId').val();
    var goodsCategoryId = $('#goodsCategoryId').val();
    var goodsName = $('#goodsName').val();
    var tag = $('#tag').val();
    var originalPrice = $('#originalPrice').val();
    var sellingPrice = $('#sellingPrice').val();
    var goodsIntro = $('#goodsIntro').val();
    var stockNum = $('#stockNum').val();
    var goodsSellStatus = $("input[name='goodsSellStatus']:checked").val();
    var goodsDetailContent = editorD.txt.html();
    var goodsCoverImg = $('#goodsCoverImg')[0].src;
    console.log("11111111"+goodsCategoryId);
    if (isNull(goodsName)) {
        swal("请输入商品名称", {
            icon: "error",
        });
        return;
    }
    if (!validLength(goodsName, 100)) {
        swal("请输入商品名称", {
            icon: "error",
        });
        return;
    }
    if (isNull(tag)) {
        swal("请输入商品小标签", {
            icon: "error",
        });
        return;
    }
    if (!validLength(tag, 100)) {
        swal("标签过长", {
            icon: "error",
        });
        return;
    }
    if (isNull(goodsIntro)) {
        swal("请输入商品简介", {
            icon: "error",
        });
        return;
    }
    if (!validLength(goodsIntro, 100)) {
        swal("简介过长", {
            icon: "error",
        });
        return;
    }
    if (isNull(originalPrice) || originalPrice < 1) {
        swal("请输入商品价格", {
            icon: "error",
        });
        return;
    }
    if (isNull(sellingPrice) || sellingPrice < 1) {
        swal("请输入商品售卖价", {
            icon: "error",
        });
        return;
    }
    if (isNull(stockNum) || sellingPrice < 0) {
        swal("请输入商品库存数", {
            icon: "error",
        });
        return;
    }
    if (isNull(goodsSellStatus)) {
        swal("请选择上架状态", {
            icon: "error",
        });
        return;
    }
    if (isNull(goodsDetailContent)) {
        swal("请输入商品介绍", {
            icon: "error",
        });
        return;
    }
    if (!validLength(goodsDetailContent, 50000)) {
        swal("商品介绍内容过长", {
            icon: "error",
        });
        return;
    }
    if (isNull(goodsCoverImg) || goodsCoverImg.indexOf('img-upload') != -1) {
        swal("封面图片不能为空", {
            icon: "error",
        });
        return;
    }
    var url = '/admin/goods/save';
    var swlMessage = '保存成功';
    var data = {
        "goodsName": goodsName,
        "goodsIntro": goodsIntro,
        "goodsCategoryId": goodsCategoryId,
        "tag": tag,
        "originalPrice": originalPrice,
        "sellingPrice": sellingPrice,
        "stockNum": stockNum,
        "goodsDetailContent": goodsDetailContent,
        "goodsCoverImg": goodsCoverImg,
        "goodsCarousel": goodsCoverImg,
        "goodsSellStatus": goodsSellStatus
    };
    console.log("1111111111111111"+data)
    if (goodsId > 0) {
        url = '/admin/goods/update';
        swlMessage = '修改成功';
        data = {
            "goodsId": goodsId,
            "goodsName": goodsName,
            "goodsIntro": goodsIntro,
            "goodsCategoryId": goodsCategoryId,
            "tag": tag,
            "originalPrice": originalPrice,
            "sellingPrice": sellingPrice,
            "stockNum": stockNum,
            "goodsDetailContent": goodsDetailContent,
            "goodsCoverImg": goodsCoverImg,
            "goodsCarousel": goodsCoverImg,
            "goodsSellStatus": goodsSellStatus
        };
    }
    console.log(data);
    $.ajax({
        type: 'POST',//方法类型
        url: url,
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function (result) {
            if (result.resultCode == 200) {
                swal({
                    title: swlMessage,
                    type: 'success',
                    showCancelButton: false,
                    confirmButtonColor: '#1baeae',
                    confirmButtonText: '返回商品列表',
                    confirmButtonClass: 'btn btn-success',
                    buttonsStyling: false
                }).then(function () {
                    window.location.href = "/admin/goods";
                })
            } else {
                swal(result.message, {
                    icon: "error",
                });
            }
            ;
        },
        error: function () {
            swal("操作失败", {
                icon: "error",
            });
        }
    });
});

$('#cancelButton').click(function () {
    window.location.href = "/admin/goods";
});
