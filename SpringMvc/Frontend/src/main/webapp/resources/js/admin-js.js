var pageContext = "";
/*var costRegex = /^([1-9])([0-9])$/;*/
var costRegex = /^([1-9])\d*$/;
var nameEn = true, nameRu = true, nameUk = true, descriptionEn = true, descriptionRu = true, descriptionUk = true, cost = true;
var paymentTypeCash = "Cash payment";
var paymentTypeCard = "Payment by card";

function changeRole(userId, buttonId) {
    var newRole = $('#' + buttonId).val();
    $.ajax({
        url : 'changeRole',
        type: 'GET',
        /*contentType: 'application/json',
        mimeType: 'application/json',*/
        data : ({
            userId: userId,
            role: newRole
        }),
        success: function () {
            $.notify(getNotificationString('role_changed'), "success");
            $("#user-role-"+userId).text(newRole);
        },
        error:function () {
            $.notify(getErrorString('role_not_changed'), "success");
        }
    });
}

function selectOption(elementId, value) {
    document.getElementById(elementId).value = value;
}

function getUserInfo(userId) {
    $.ajax({
        url : 'getUserInfo',
        type: 'GET',
        data : ({
            userId : userId
        }),
        dataType:'json',
        success: function (data) {
            if (data.image !== null && data.image !== "" && typeof data.image !== "undefined"){
                $("#data-user-image").attr('src', pageContext + data.image);
            }else{
                $("#data-user-image").attr('src', pageContext + "/resources/img/avatars/default.jpg");
            }
            $("#data-role").text(data.role);
            $("#data-user-id").text(data.userId);
            $("#data-login").text(data.login);
            $("#data-fio").text(data.fio);
            $("#data-phone").text(data.phoneNumber);
            $("#data-email").text(data.email);
            if (data.birthday != null){
                $("#data-birthday").text(new Date(data.birthday.year, data.birthday.month - 1, data.birthday.day).toLocaleDateString());
            }
            $.each(data.addresses, function( index, value ) {
                getAddressByCoordinates(value.latitude,value.longitude, index);
            });
        },
        error: function () {
           $.notify(getErrorString('data_error'));
        }
    });
}

function getAddressByCoordinates(latitude, longitude, index) {
    var coords = [latitude, longitude];
    ymaps.geocode(coords).then(function (res) {
        if (res.geoObjects.get(0) != null) {
            var obj = res.geoObjects.get(0);
            var html;
            if (index === 0){
                html = $("#user-data-list").html() + "<li class=\"user-address\"><p class=\"row\"><span class=\"col-sm-1\"></span><span class=\"col-sm-3\">" + getLocStrings('addresses') + "</span><span class=\"data-address\" class=\"col-sm-6\">" + obj.getAddressLine() + "</span></p></li>";
            }else{
                html = $("#user-data-list").html() + "<li class=\"user-address\"><p class=\"row\"><span class=\"col-sm-1\"></span><span class=\"col-sm-3\"></span><span class=\"data-address\" class=\"col-sm-6\">" + obj.getAddressLine() + "</span></p></li>";
            }
            $("#user-data-list").html(html);
        }
    });
}

function removeUser(thisElem, userId) {
    $.ajax({
        url : 'removeUser',
        type: 'GET',
        data : ({
            userId: userId
        }),
        success: function () {
            $.notify(getNotificationString('user_deleted'), "success");
            //$(thisElem).parent('td').parent('tr').remove();
            var userTable = $("#users-table").DataTable();
            userTable
                .row($(thisElem).parents('tr'))
                .remove()
                .draw();
        },
        error: function () {
            $.notify(getErrorString('user_not_deleted'), "error");
        }
    });
}

function createUser() {
    var userFormArray = $("#createUserForm").serializeArray();
    var userObj = {};
    $.each(userFormArray, function(idx, el){
        userObj[el.name] = el.value;
       // userObj[el.name] ? userObj[el.name].push(el.value) : (userObj[el.name] = [el.value]);
    });
    $.ajax({
        url : 'createUser',
        type: 'GET',
        data : ({
            jsonUser : JSON.stringify(userObj)
        }),
        dataType: 'json',
        success: function (data) {
            $.notify(getNotificationString('user_created'), "success");
            $("#createUserForm").trigger('reset');
            $("#btn-signUp").prop('disabled', true);
            $('#createUserForm input').each(function(nf, form){
                $(this).css('border', '0.5px solid #b4b3b3');
            });

            var row = "<tr class=\"user-tr\" onclick=\"getUserInfo('" + data.userId + "');\">" +
                "<td data-toggle=\"modal\" data-target=\"#user-info-modal\">" + data.userId + "</td>" +
                "<td data-toggle=\"modal\" data-target=\"#user-info-modal\">" + data.fio + "</td>" +
                "<td data-toggle=\"modal\" data-target=\"#user-info-modal\">" + data.login + "</td>" +
                "<td data-toggle=\"modal\" data-target=\"#user-info-modal\"></td>" +
                "<td data-toggle=\"modal\" data-target=\"#user-info-modal\" id=\"user-role-"+ data.userId + "\">" + data.role + "</td>" +
                "<td class=\"text-center\">" +
                "<select id=\"dropdown-" + data.userId + "\" class=\"select-each-role\" style =\"margin-right: 5px;\">" +
                "   <option value=\"ROLE_COURIER\">" + getLocStrings('ROLE_COURIER') + "</option>" +
                "   <option value=\"ROLE_ADMIN\">" + getLocStrings('ROLE_ADMIN') + "</option>" +
                "   <option value=\"ROLE_USER\">" + getLocStrings('ROLE_USER') + "</option>" +
                "</select>" +
                "<a class='btn btn-info btn-xs' style =\"margin-right: 5px;\" href=\"#\"\n" +
                "   onclick=\"changeRole('" + data.userId + "','dropdown-" + data.userId + "');\">" +
                "       <span class=\"glyphicon glyphicon-edit\"></span>" + getLocStrings('change_role') +
                "</a>" +
                "<a href=\"#\" class=\"btn btn-danger btn-xs\"\n" +
                "   onclick=\"removeUser(this,'" + data.userId + "');\">" +
                "       <span class=\"glyphicon glyphicon-remove\"></span>" + getLocStrings('del_user') +
                "</a>" +
                "</td> </tr>";
            var userTable = $("#users-table").DataTable();
            userTable.row.add($(row)).draw();
            //$('#users-table tr:last').after(row);
        },
        error: function () {
            $.notify(getErrorString('user_not_created'), "error");
        }
    });
}

function getOrderInfo(orderId) {
    $.ajax({
        url : 'getOrderInfo',
        type: 'GET',
        data : ({
            orderId : orderId
        }),
        dataType:'json',
        success: function (data) {
            $("#order-id").text(data.orderId);
            $("#order-status").text(data.status);
            var date = new Date(data.orderCreationDate.date.year, data.orderCreationDate.date.month - 1, data.orderCreationDate.date.day, data.orderCreationDate.time.hour, data.orderCreationDate.time.minute, data.orderCreationDate.time.second);
            $("#order-date").text(date.toLocaleString());
            getOrderAddress(data.orderAddress.latitude, data.orderAddress.longitude);
            $("#order-phone").text(data.orderPhone);
            $("#order-payment-type").text(data.paymentType);
            if (data.paymentType === paymentTypeCash){
                if (data.changeFrom != null && data.changeFrom !== ""){
                    $("#order-payment-type").text($("#order-payment-type").text() + " (" + getLocStrings('change_from') + " " + data.changeFrom + getLocStrings('rub') + ")");
                }
            }
            if(data.isPaid === true){
                $("#order-paid").text(getLocStrings('order_paid'));
            }else{
                $("#order-paid").text(getLocStrings('order_not_paid'));
            }
            //alert(JSON.stringify(data));
            for (var i = 0; i < data.orderItems.length; i++){
                var newItemHtml = "<div class=\"row text-left\">" +
                    "               <div class=\"col-sm-5\">" + data.orderItems[i].productName + "</div>" +
                    "               <div class=\"col-sm-4\">" + data.orderItems[i].productQuantity + getLocStrings('items_count') + getLocStrings('multiplication_sign') + data.orderItems[i].productCost + getLocStrings('rub') +
                    "               </div>" +
                    "               <div class=\"col-sm-3\">" + data.orderItems[i].productCost*data.orderItems[i].productQuantity + getLocStrings('rub') + "</div>" +
                    "              </div>";
                $("#order-items").append(newItemHtml);
            }
            $("#order-cost").text(data.orderCost + getLocStrings('rub'));

            $.ajax({
                    url : 'getUserInfo',
                    type: 'GET',
                    data : ({
                        userId : data.userId
                    }),
                    dataType:'json',
                    success: function (data) {
                        $("#user-role-order").text(data.role);
                        $("#user-id-order").text(data.userId);
                        $("#user-login-order").text(data.login);
                        $("#user-fio-order").text(data.fio);
                        $("#user-phone-order").text(data.phoneNumber);
                        $("#user-email-order").text(data.email);
                        $("#user-birthday-order").text(new Date(data.birthday.year, data.birthday.month - 1, data.birthday.day).toLocaleDateString());
                        /*$.each(data.addresses, function( index, value ) {
                            getAddressByCoordinates(value.latitude,value.longitude, index);
                        });*/
                    },
                    error: function () {
                        $.notify(getErrorString('data_error'));
                    }
            });
            if (data.courierId != null && data.courierId !== 0){
                $.ajax({
                    url : 'getUserInfo',
                    type: 'GET',
                    data : ({
                        userId : data.courierId
                    }),
                    dataType:'json',
                    success: function (data) {
                        $("#courier-role-order").text(data.role);
                        $("#courier-id-order").text(data.userId);
                        $("#courier-login-order").text(data.login);
                        $("#courier-fio-order").text(data.fio);
                        $("#courier-phone-order").text(data.phoneNumber);
                        $("#courier-email-order").text(data.email);
                        $("#courier-birthday-order").text(new Date(data.birthday.year, data.birthday.month - 1, data.birthday.day).toLocaleDateString());
                        /*$.each(data.addresses, function( index, value ) {
                            getAddressByCoordinates(value.latitude,value.longitude, index);
                        });*/
                    },
                    error: function () {
                        $.notify(getErrorString('data_error'));
                    }
                });
            }
        },
        error: function () {
            $.notify(getErrorString('data_error'));
        }
    });
}

function getOrderAddress(latitude, longitude){
    var coords = [latitude, longitude];
    ymaps.geocode(coords).then(function (res) {
        if (res.geoObjects.get(0) != null) {
            var obj = res.geoObjects.get(0);
            $("#order-address").text(obj.getAddressLine());
        }
    });
}

function editItem(itemId) {
    $.ajax({
        url : 'getItemInfo',
        type: 'GET',
        data : ({
            itemId : itemId
        }),
        dataType:'json',
        success: function (data) {
            $(".item-id").val(data.productId);
            $("#item-category").val(data.productCategory);
            $("#item-cost").val(data.productCost);
            $("#item-image").attr('src', pageContext + data.productImage);
        },
        error: function () {
            $.notify(getErrorString('data_error'));
        }
    });

    $.ajax({
        url : 'getLocItemsInfo',
        type: 'GET',
        data : ({
            itemId : itemId
        }),
        dataType:'json',
        success: function (data) {
            $("#item-name-en").val(data.nameEn);
            $("#item-description-en").val(data.descriptionEn);
            $("#item-name-ru").val(data.nameRu);
            $("#item-description-ru").val(data.descriptionRu);
            $("#item-name-uk").val(data.nameUk);
            $("#item-description-uk").val(data.descriptionUk);
        },
        error: function () {
            $.notify(getErrorString('data_error'));
        }
    });
}

function removeItem(thisElem, itemId) {
    $.ajax({
        url : 'delItem',
        type: 'GET',
        data : ({
            itemId: itemId
        }),
        success: function () {
            $.notify(getNotificationString('item_deleted'), "success");
            //$(thisElem).parent('td').parent('tr').remove();
            var itemTable = $("#items-table").DataTable();
            itemTable
                .row($(thisElem).parents('tr'))
                .remove()
                .draw();
        },
        error: function () {
            $.notify(getErrorString('item_not_deleted'), "error");
        }
    });
}

function removeOrder(thisElem, orderId) {
    $.ajax({
        url : 'delOrder',
        type: 'GET',
        data : ({
            orderId: orderId
        }),
        success: function () {
            $.notify(getNotificationString('order_deleted'), "success");
            var ordersTable = $("#orders-table").DataTable();
            ordersTable
                .row($(thisElem).parents('tr'))
                .remove()
                .draw();
        },
        error: function () {
            $.notify(getErrorString('order_not_deleted'), "error");
        }
    });
}

function setOrderId(orderId) {
    $("#order-id-for-courier").val(orderId);
}

function getCouriersNumOrders(courierId){
    $.ajax({
        url : 'getNumOfOrders',
        type: 'GET',
        data: ({
            courierId: courierId
        }),
        success: function (data) {
            $("#num-orders-" + courierId).text(data);
        },
        error: function () {
            $.notify(getErrorString('data_error'), "error");
        }
    });
}

function saveSetCourier() {
    var courierId = $("input[name=courier-choice]:checked").val();
    var orderId = $("#order-id-for-courier").val();
    if (typeof courierId !== "undefined" && courierId != null && typeof orderId !== "undefined" && orderId !== null){
        $.ajax({
            url : 'setCourier',
            type: 'GET',
            data: ({
                courierId: courierId,
                orderId: orderId
            }),
            success: function () {
                $('#set-courier-modal').modal('hide');
                $.notify(getNotificationString('courier_appointed'), "success");
                $("#order-status-" + orderId).text(getLocStrings('status_with_courier'));
                $("#order-row-" + orderId).find(".order-courier").text(courierId);
                $("#order-set-courier-btn-" + orderId).remove();
                //$("#order-courier-" + courierId).text(courierId);
            },
            error: function () {
                $('#set-courier-modal').modal('hide');
                $.notify(getErrorString('data_error'), "error");
            }
        });
    }
}

function checkEditItemsFields() {
    if (nameEn && nameRu && nameUk && descriptionEn && descriptionRu && descriptionUk && cost) {
        $("#save-item-info-btn").prop('disabled', false);
    } else {
        $("#save-item-info-btn").prop('disabled', true);
    }
}

$(document).ready(function () {

    var languageTableParams = {
        "language":{
            "lengthMenu": getLocStrings('show') + " _MENU_ " + getLocStrings('entries'),
            "zeroRecords": getLocStrings('no_records found'),
            "info": getLocStrings('showing_page') + " _PAGE_ " + getLocStrings('page_of') +" _PAGES_",
            "infoEmpty": getLocStrings('no_records_available'),
            "infoFiltered": "(" + getLocStrings('filtered_from') + " _MAX_ " + getLocStrings('total_records') +")",
            "search": getLocStrings('search'),
            "processing": getLocStrings('processing'),
            "loadingRecords": getLocStrings('loading_records'),
            "emptyTable": getLocStrings('empty_table'),
            paginate: {
                first:      getLocStrings('first'),
                previous:   getLocStrings('previous'),
                next:       getLocStrings('next'),
                last:       getLocStrings('last')
            },
            aria: {
                sortAscending:  getLocStrings('sort_ascending'),
                sortDescending: getLocStrings('sort_descending')
            }
        }
    };

    var ordersTable = $("#orders-table").DataTable(languageTableParams);
    var usersTable = $("#users-table").DataTable(languageTableParams);
    var itemsTable = $("#items-table").DataTable(languageTableParams);
    var couriersTable = $("#couriers-table").DataTable(languageTableParams);


    $("#user-info-modal").on("hide.bs.modal", function () {
        $(".user-address").remove();
        $(".user-value").text("");
    });

    $("#order-info-modal").on("hide.bs.modal", function () {
        $("#order-items div").remove();
        $(".user-value-order").text("");
        $(".order-value").text("");
    });

    $(".item-param").keyup(function () {
        if ($(this).val() === ""){
            $(this).css("border", "0.5px solid #d9534f");
        }else{
            $(this).css("border", "0.5px solid #5cb85c");
        }
    });

    $('#item-cost').keyup(function () {
        if ($(this).val() === "0"){
            $(this).val("");
            $(this).css("border", "0.5px solid #d9534f");
        }
        var testText = $(this).val();
        if (testText*1 + 0 != $(this).val()){
            $(this).val(testText.substring(0, testText.length - 1));
        }
    });

    $("#my-file-selector").change(function () {
        var fileName = this.files[0].name;
        var fileType = this.files[0].type;
        if (fileType !== "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" && fileType !== "application/vnd.ms-excel") {
            $.notify(getErrorString('invalid_file'), "error");
            this.value = "";
            $('#upload-file-info').html("");
        } else {
            $('#upload-file-info').html(fileName);
        }
    });

    $( "#add-item-form" ).submit(function( event ) {
        if ( $("#my-file-selector").val() === "" ) {
            $.notify(getErrorString('invalid_file'), "error");
            event.preventDefault();
        }else{
            $( "#add-item-form" ).submit();
        }
    });

    /*item image*/

    $('#item-image').on('click', function () {
        $('#item-image-upload').click();
    });
    $('#item-image-upload').change(function(){
        readURL(this);
    });

    function readURL(input) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();
            reader.onload = function(e) {
                $('#item-image').attr('src', e.target.result);
            };
            reader.readAsDataURL(input.files[0]);
        }
    }

    $("#edit-item-modal").on("hide.bs.modal", function () {
        $("#update-item-form").trigger('reset');
    });

    /*Item validate*/

    $('#item-name-en').keyup(function () {
        //$('#fio').css("box-shadow", "none");
        if ($('#item-name-en').val() == "") {
            nameEn = false;
        } else {
            nameEn = true;
        }
        checkEditItemsFields();
    });
    $('#item-name-ru').keyup(function () {
        //$('#fio').css("box-shadow", "none");
        if ($('#item-name-ru').val() == "") {
            nameRu = false;
        } else {
            nameRu = true;
        }
        checkEditItemsFields();
    });
    $('#item-name-uk').keyup(function () {
        //$('#fio').css("box-shadow", "none");
        if ($('#item-name-uk').val() == "") {
            nameUk = false;
        } else {
            nameUk = true;
        }
        checkEditItemsFields();
    });
    $('#item-description-en').keyup(function () {
        //$('#fio').css("box-shadow", "none");
        if ($('#item-description-en').val() == "") {
            descriptionEn = false;
        } else {
            descriptionEn = true;
        }
        checkEditItemsFields();
    });
    $('#item-description-ru').keyup(function () {
        //$('#fio').css("box-shadow", "none");
        if ($('#item-description-ru').val() == "") {
            descriptionRu = false;
        } else {
            descriptionRu = true;
        }
        checkEditItemsFields();
    });
    $('#item-description-uk').keyup(function () {
        //$('#fio').css("box-shadow", "none");
        if ($('#item-description-uk').val() == "") {
            descriptionUk = false;
        } else {
            descriptionUk = true;
        }
        checkEditItemsFields();
    });
    $('#item-cost').keyup(function () {
        //$('#fio').css("box-shadow", "none");
        if ($('#item-cost').val() == "") {
            cost = false;
        } else {
            cost = true;
        }
        checkEditItemsFields();
    });
});



