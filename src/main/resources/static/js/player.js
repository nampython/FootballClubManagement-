
$(document).ready(function () {
    init();
});

/**
 * Init page.
 */
init = function () {
    setup();
    events();
    nestedFunc();
    dataClub();

}; // End method init

/***
  * Sets up page methods needed for initial.
  * Just define methods here, keeps them in page scope.  Call them in initial.
***/
setup = function () {
    createDomCache();
}

/**
 * @date 2022-04-15
 * @returns {any}
 */
createDomCache = function () {
    domCache = {
        $body: $('body')
    };
    domCache.$btnHomePlayer = domCache.$body.find('#home-button');
    domCache.$btnRefreshPlayer = domCache.$body.find('#refresh-button');
    domCache.$btnDeletePlayer = domCache.$body.find('#delete-button');
    domCache.$btnEditPlayer = domCache.$body.find('#edit-button');
    domCache.$btnAddPlayer = domCache.$body.find('#add-button');
    domCache.$tablePlayer = domCache.$body.find('#playertable');
}


/**
 * Nesscessary events for Player Management. 
 * @date 2022-04-15
 * @returns {any}
 */
events = function () {

    /**
     * When click Button Home on Player's Page, Return Home page
     * @date 2022-04-15
     * @param {any} function(
     * @returns {any}
     */
    domCache.$btnHomePlayer.click(function () {
        window.location.replace(localIp + 'home');
    });

    /**
     * When click Button Refreshe on Player's Page, Refresh Player Page
     * @date 2022-04-15
     * @param {any} function(
     * @returns {any}
     */
    domCache.$btnRefreshPlayer.click(function () {
        window.location.replace(localIp + 'players');
    });

    /**
     * When click Button Delete on Player's Page, Delete a player on the tablePlayer
     * @date 2022-04-15
     * @param {any} function(
     * @returns {any}
     */
    domCache.$btnDeletePlayer.click(function () {
        var gr = domCache.$tablePlayer.jqGrid('getGridParam', 'selrow');
        if (gr != null) {
            var idClub = $("#" + gr).find("td:eq(0)").html();
            console.log(idClub);
            domCache.$tablePlayer.jqGrid('delGridRow', gr, {
                mtype: "DELETE",
                reloadAfterSubmit: true,
                url: localIp + 'api/players/' + idClub,
            });
        }
        else {
            alert("Please Select Row to delete!");
        }

    });

    /**
     * When click Button Edit on Player's Page, Edit a player on the tablePlayer
     * @date 2022-04-15
     * @param {any} function(
     * @returns {any}
     */
    domCache.$btnEditPlayer.click(function () {
        var gr = domCache.$tablePlayer.jqGrid('getGridParam', 'selrow');
        if (gr != null) {
            var idplayer = $("#" + gr).find("td:eq(0)").html();
            domCache.$tablePlayer.jqGrid('editGridRow', gr, {
                beforeShowForm: function (form) {
                    tickPlayer(form)
                },
                afterSubmit: function (response, postdata) {
                    domCache.$tablePlayer.jqGrid('setGridParam', { datatype: 'json' }).trigger('reloadGrid');
                },
                mtype: "PUT",
                reloadAfterSubmit: true,
                closeAfterEdit: true,
                url: localIp +  'api/players/' + idplayer,
            });
        }
        else {
            alert("Please Select Row");
        }
    });

    /**
     * When click Button ADd on Player's Page, Add a player and save the player to the database
     * @date 2022-04-15
     * @param {any} function(
     * @returns {any}
     */
    domCache.$btnAddPlayer.click(function () {
        domCache.$tablePlayer.jqGrid('editGridRow', "new", {
            height: 350,
            reloadAfterSubmit: true,
            closeAfterEdit: true,
            mtype: "POST",
            url: localIp + 'api/players/',
            beforeShowForm: function (form) {
                $("#tr_playerId").val(0);
                tickPlayer(form);
            },
            afterSubmit: function (response, postdata) {
                domCache.$tablePlayer.jqGrid('setGridParam', { datatype: 'json' }).trigger('reloadGrid');
            }
        });
    });
    
    domCache.$tablePlayer.bind("jqGridAddEditBeforeShowForm", function (form) {


    });
}

/**
 * To create Training Player
 * @date 2022-04-15
 * @param {any} listClub
 * @returns {any}
 */
loadConfigs = function (listClub) {
    domCache.$tablePlayer.jqGrid({
        caption: "Training Player",
        url: localIp + 'api/players/',
        datatype: 'json',
        sortorder: "asc",
        mtype: 'GET',
        colNames: ['Id', 'Player Name', 'Date Of Birth', 'National', 'Height (cm)', 'Left Foot', 'Right Foot', 'Current Club'],
        colModel: [
            {
                name: 'id',
                label: "id",
                align: "center",
                width: 50,
                editable: true,
                editoptions:
                {
                    readonly: true,
                    size: 10,
                },
            },
            {
                name: "playerName",
                label: "playerName",
                align: "center",
                editable: true,
                width: 150,
                editoptions: {
                    maxlength: 40, // length 40

                },
                editrules: {
                    custom_func: validateWordAndNumber,
                    custom: true,
                    required: true,
                },
            },
            {
                name: 'dateOfBirth',
                index: 'dateOfBirth',
                width: 120,
                align: "center",
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
                name: "national",
                label: "national",
                align: "center",
                editable: true,
                width: 100,
                editoptions: {
                    maxlength: 40, // length 40
                },
                editrules: {
                    custom_func: validateWordAndNumber,
                    custom: true,
                    required: true,
                },
            },
            {
                name: "height",
                label: "height",
                align: "center",
                editable: true,
                width: 120,
                editoptions: {
                    maxlength: 3
                },
                editrules: {
                    custom_func: isvalidHeight,
                    custom: true,
                    required: true,
                },
            },
            {
                name: "leftFooted",
                label: "leftFooted",
                align: "center",
                editable: true, // alow tick on submit form
                formatter: "checkbox", // show checkbox on screen
                edittype: "checkbox", // show checkbox on submit form
                editoptions: {
                    value: "true:false" // receive 2 value true or false 
                },
                formatoptions: {
                    disabled: true // allow edit inline
                },
                search: false,
            },

            {
                name: "rightFooted",
                label: "rightFooted",
                align: "center",
                editable: true, // alow tick on submit form
                formatter: "checkbox", // show checkbox on screen
                edittype: "checkbox", // show checkbox on submit form
                editoptions: {
                    value: "true:false" // receive 2 value true or false 
                },
                formatoptions: {
                    disabled: true // allow edit inline
                },
                search: false,
            },
            {
                name: "club.id",
                index: 'club',
                align: "center",
                editable: true,
                width: 200,
                edittype: "select",
                formatter: "select",
                editoptions: {
                    value: listClub,
                    dataInit: select2
                },
                editrules: {
                    required: true,
                },
            },
        ],
        loadonce: true,
        pager: "#tbPlayerPager",
        rowNum: 10,
        rowList: [10, 20, 30],
        loadComplete: function (data) {
            domCache.$body.find('.s-ico').show();
        }
        // onSelectRow: function (id) {
        //     if (id && id !== lastsel2) {
        //         tbPlayer.restoreRow(lastsel2);
        //         lastsel2 = id;
        //     }
        // },
    }).filterToolbar({
        groupOp: 'AND', // Allow User to combine many operators on columns to print data
        searchOnEnter: false, // false is when user is not to need press Enter, Data is automatically sorted for user
    });

}
