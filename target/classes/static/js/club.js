$(document).ready(function () {
    var domCache = {};
    init();
});

/**
 * Init page.
 */
init = function () {
    setup();
    events();
    nestedFunc();
    loadConfigs();

}; // End method init

/***
  * Sets up page methods needed for initial.
  * Just define methods here, keeps them in page scope.  Call them in initial.
***/
setup = function () {
    createDomCache();
}

/**
 * Initial buttons
 * @date 2022-04-15
 * @returns {any}
 */
createDomCache = function () {
    domCache = {
        $body: $('body')
    };
    domCache.$btnHomeClub = domCache.$body.find('#home-button');
    domCache.$btnRefreshClub = domCache.$body.find('#refresh-button');
    domCache.$btnDeleteClub = domCache.$body.find('#delete-button');
    domCache.$btnEditClub = domCache.$body.find('#edit-button');
    domCache.$btnAddClub = domCache.$body.find('#add-button');
    domCache.$tableClub = domCache.$body.find('#clubTable');
}

/**
 * Nesscessary events for Club Management. 
 * @date 2022-04-15
 * @returns {any}
 */
events = function () {

    /**
     * When click Button Home on Club's Page, Return Home page
     * @date 2022-04-15
     * @param {any} function(
     * @returns {any}
     */
    domCache.$btnHomeClub.click(function () {
        window.location.replace(localIp + 'home');
    });

    /**
     * When click Button Home on Club's Page, Refresh Player Page
     * @date 2022-04-15
     * @param {any} function(
     * @returns {any}
     */
    domCache.$btnRefreshClub.click(function () {
        window.location.replace(localIp + 'clubs');
    });

    /**
     * When click Button Delete on Player's Page, Delete a player on the tablePlayer
     * @date 2022-04-15
     * @param {any} function(
     * @returns {any}
     */
    domCache.$btnDeleteClub.click(function () {
        var gr = domCache.$tableClub.jqGrid('getGridParam', 'selrow');
        console.log(gr)
        if (gr != null) {
            var idClub = $("#" + gr).find("td:eq(0)").html();
            console.log(idClub);
            jQuery("#clubTable").jqGrid('delGridRow', gr, {
                mtype: "DELETE",
                reloadAfterSubmit: true,
                url: localIp + 'api/clubs/' + idClub,
            });
        }
        else {
            alert("Please Select Row to delete!");
        }

    });

    /**
     * When click Button Edit on Club's Page, Edit a Club on the tablePlayer
     * @date 2022-04-15
     * @param {any} function(
     * @returns {any}
     */
    domCache.$btnEditClub.click(function () {
        var gr = domCache.$tableClub.jqGrid('getGridParam', 'selrow');
        if (gr != null) {
            var idClub = $("#" + gr).find("td:eq(0)").html();
            jQuery("#clubTable").jqGrid('editGridRow', gr, {
                beforeShowForm: function (form) {
                    //disabled input id for edit
                    tickClub(form)
                },
                afterSubmit: function(response, postdata) {
                    domCache.$tableClub.jqGrid('setGridParam', {datatype: 'json'}).trigger('reloadGrid');
                },
                mtype: "PUT",
                reloadAfterSubmit: true,
                closeAfterEdit: true,
                url: localIp + 'api/clubs/' + idClub,
            });
        }
        else {
            alert("Please Select Row");
        }
    });

    /**
     * When click Button Add on Club's Page, Add a Club and save  the club to the database
     * @date 2022-04-15
     * @param {any} function(
     * @returns {any}
     */
    domCache.$btnAddClub.click(function () {
        domCache.$tableClub.jqGrid('editGridRow', "new", {
            height: 350,
            reloadAfterSubmit: true,
            mtype: "POST",
            url: localIp +  'api/clubs/',
            beforeShowForm: function (form) {
                $("#id").val(0);
                tickClub(form);
            },
            afterSubmit: function(response, postdata) {
            	domCache.$tableClub.jqGrid('setGridParam', {datatype: 'json'}).trigger('reloadGrid');
            }
        });
    });
}

/**
 * Functions for Club Management
 * @date 2022-04-15
 * @returns {any}
 */
nestedFunc = function () {

    
}

/**
 * To create Training Club
 * @date 2022-04-15
 * @param {any} listClub
 * @returns {any}
 */
loadConfigs = function () {
    domCache.$tableClub.jqGrid({
        caption: "Training Club",
        url: localIp + 'api/clubs',
        datatype: 'json',
        sortorder: "asc",
        mtype: 'GET',
        colNames: ['Id', 'Club Name', 'Club abbrev', 'Founding date', 'Stadium', 'Tournament', 'About'],
        colModel: [
            {
                name: 'id',
                label: "id",
                width: 50,
                sorttype: "long",
                editable: true,
                editoptions:
                {
                    readonly: true,
                    size: 10
                }
            },
            {
                name: "clubName",
                label: "clubName",
                editable: true,
                width: 150,
                editoptions: {
                    maxlength: 40, // length 40
                },
                editrules: {
                    custom_func: isExistingClub,
                    custom: true,
                    required: true
                },
            },
            {
                name: "abbrev",
                label: "abbrev",
                editable: true,
                width: 100,
                editoptions: {
                    maxlength: 20, // length 20
                },
                editrules: {
                    custom_func: isvalidAbbrev,
                    custom: true,
                    required: true
                },
            },
            {
                name: 'date',
                index: 'date',
                width: 120,
                formatter: "date",
                formatoptions: {
                    newformat: "d/m/Y"
                },
                editoptions: {
                    size: 10,
                    maxlengh: 10,
                    dataInit: function (element) {
                        $(element).datepicker({
                            dateFormat: 'dd/mm/yy',
                            changeMonth: true,
                            changeYear: true,
                            yearRange: "1900:",
                            maxDate: "now"
                        })
                    },

                },
                editable: true,
                editrules: {
                    required: true,
                    custom: true,
                    custom_func: isvalidDate
                },
            },

            {
                name: "stadium",
                label: "stadium",
                editable: true,
                width: 120,
                editoptions: {
                    maxlength: 40, // length 40
                },
                editrules: {
                    custom_func: isvalidAbbrev,
                    custom: true,
                    required: true
                },
            },
            {
                name: "tournament",
                label: "tournament",
                editable: true,
                width: 150,
                edittype: "select",
                formatter: "select",
                editoptions: { 
                    value: tournamentData,
                    dataInit: select2
                }
            },
            {
                name: "about",
                label: "about",
                editable: true,
                edittype: "textarea",
                editoptions: {
                    rows: "5",
                    cols: "5",
                    length: 200 // length 200
                },  
            },
        ],
        loadComplete: function(data) {
            domCache.$body.find('.s-ico').show();
        },
        loadonce: true,
        pager: "#tbClubPager",
        rowNum: 10,
        rowList: [10, 20, 30],
    }).filterToolbar({
        groupOp: 'AND', // Allow User to combine many operators on columns to print data
        searchOnEnter: false, // false is when user is not to need press Enter, Data is automatically sorted for user
    });
}
