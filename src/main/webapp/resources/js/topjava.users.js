// $(document).ready(function () {
function switchEnabled(id){
    $.ajax({
        type:"POST",
        url:"ajax/admin/users/enable/" + id,
        data: {enabled: $('#userEnable').prop('checked')}
    }).done($.get("ajax/admin/users/", updateTableByData));
}

$(function () {
    makeEditable({
            ajaxUrl: "ajax/admin/users/",
            datatableApi: $("#datatable").DataTable({
                "paging": false,
                "info": true,
                "columns": [
                    {
                        "data": "name"
                    },
                    {
                        "data": "email"
                    },
                    {
                        "data": "roles"
                    },
                    {
                        "data": "enabled"
                    },
                    {
                        "data": "registered"
                    },
                    {
                        "defaultContent": "Edit",
                        "orderable": false
                    },
                    {
                        "defaultContent": "Delete",
                        "orderable": false
                    }
                ],
                "order": [
                    [
                        0,
                        "asc"
                    ]
                ]
            }),
        updateTable: function () {
                $.get("ajax/admin/users/", updateTableByData)

        }
        }
    );
});