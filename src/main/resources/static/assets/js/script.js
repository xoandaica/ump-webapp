$(document).ready(function() {
    //$('#DataTables_Table_0').dataTable();
    // var devices = ${devices};




    $('#selectPage').on('change', function() {
        if (this.value != 'default') {
            console.log(this.value);
            window.location.href = this.value
        }
    });

    $('#checkAll').change(function() {
        if ($(this).is(":checked")) {
            checkAll();
        } else {
            unCheckAll();
        }

    });
    $('#btnReboot').click(function() {
        console.log("click btn reboot");
        var isReboot = confirm('Reboot!!! Are you sure?');
        if (isReboot) {
            $('.myCheckBox').each(function(index, obj) {
                var id = $(this).attr('id');
                if ($(this).is(':checked')) {
                var url = "/devices/" + id + "/reboot(deviceId="+ id +",timeout='3000',now='false')" ;
                console.log(url);
                    $.get( "/devices/" + id + "/reboot(deviceId="+ id +",timeout='3000',now='false')" );

                }
            });
        }
    });

    $('#btnResetFactory').click(function() {
        console.log("click btn resetFacto");
        var isReset = confirm('ResetFactory!!! Are you sure?');
        if (isReset) {
            $('.myCheckBox').each(function(index, obj) {
                var id = $(this).attr('id');
                if ($(this).is(':checked')) {
                    console.log("device" + id + "reboot");
                }
            });
        }
    });


    function checkAll() {
        $('.myCheckBox').each(function(index, obj) {
            //            console.log('div' + index + ':' + $(this).attr('id'));
            var id = $(this).attr('id');
            $('#' + id).prop('checked', true);
        });

    }

    function unCheckAll() {
        $('.myCheckBox').each(function(index, obj) {
            //            console.log('div' + index + ':' + $(this).attr('id'));
            var id = $(this).attr('id');
            $('#' + id).prop('checked', false);
        });
    }


});