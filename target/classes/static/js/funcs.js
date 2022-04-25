
var tournamentData = " Premier League/League 1: Premier League/League 1;La Liga:La Liga;Bundesliga:Bundesliga"

/**
 * Functions for Player Management
 * @date 2022-04-15
 * @returns {any}
 */
nestedFunc = function () {

    /**To create dropdown that contains club data from player
     * @date 2022-04-15
     * @returns {any}
     */
     var listClub = "";
     dataClub = function () {
         $.ajax({
             url: localIp + 'api/clubs',
             success: function (result) {
                 result.map((item, index) => {
                     listClub += item.clubId + ":" + item.name;
                     if (index < result.length - 1) {
                         listClub += ";";
                     }
                 })
                 loadConfigs(listClub);
             }
         })
    }

    /**
     * Display red ticks for the player submission
     * @date 2022-04-25
     * @param {any} form
     * @returns {any}
     */
    tickPlayer = function (form) {
        var $trId = form.find('#tr_id   '),
            // $idCaption = form.find('#tr_id .CaptionTD:eq(0)');
            $playerNameCaption = form.find('#tr_playerName .CaptionTD:eq(0)');
        $dobNameCaption = form.find('#tr_dateOfBirth .CaptionTD:eq(0)');
        $nationalCaption = form.find('#tr_national .CaptionTD:eq(0)')
        $nationalNameCaption = form.find('#tr_national .CaptionTD:eq(0)')
        $heightCaption = form.find('#tr_height .CaptionTD:eq(0)')
        $leftCaption = form.find('#tr_leftFooted .CaptionTD:eq(0)')
        $rightCaption = form.find('#tr_rightFooted .CaptionTD:eq(0)')
        $currentClubCaption = form.find('#tr_clubId .CaptionTD:eq(0)')
        //disabled input id for edit
        $trId.hide();
        $playerNameCaption.html('<span class="isRequiredIndicator">' + $playerNameCaption.html() + '</span>');
        $dobNameCaption.html('<span class="isRequiredIndicator">' + $dobNameCaption.html() + '</span>');
        $nationalCaption.html('<span class="isRequiredIndicator">' + $nationalCaption.html() + '</span>');
        $heightCaption.html('<span class="isRequiredIndicator">' + $heightCaption.html() + '</span>');
        $leftCaption.html('<span class="optional">' + $leftCaption.html() + '</span>');
        $rightCaption.html('<span class="optional">' + $rightCaption.html() + '</span>');
        $currentClubCaption.html('<span class="isRequiredIndicator">' + $currentClubCaption.html() + '</span>');
    }

    /**
     * Display red ticks for the club submission
     * @date 2022-04-25
     * @param {any} form
     * @returns {any}
     */
    tickClub = function (form) {
        var $trId = form.find('#tr_id')
        $clubNameCaption = form.find('#tr_clubName .CaptionTD:eq(0)');
        $abbrevCaption = form.find('#tr_abbrev .CaptionTD:eq(0)');
        $dobCaption = form.find('#tr_date .CaptionTD:eq(0)')
        $stadiumCaption = form.find('#tr_stadium .CaptionTD:eq(0)')
        $tournamentCaption = form.find('#tr_tournament .CaptionTD:eq(0)')
        $aboutCaption = form.find('#tr_about .CaptionTD:eq(0)')
        $trId.hide();
        $clubNameCaption.html('<span class="isRequiredIndicator">' + $clubNameCaption.html() + '</span>');
        $abbrevCaption.html('<span class="isRequiredIndicator">' + $abbrevCaption.html() + '</span>');
        $dobCaption.html('<span class="isRequiredIndicator">' + $dobCaption.html() + '</span>');
        $stadiumCaption.html('<span class="isRequiredIndicator">' + $stadiumCaption.html() + '</span>');
        $tournamentCaption.html('<span class="isRequiredIndicator">' + $tournamentCaption.html() + '</span>');
        $aboutCaption.html('<span class="optional">' + $aboutCaption.html() + '</span>');
    }


    /**
     * Check if one of the club is exising
     * @date 2022-04-25
     * @param {any} value
     * @param {any} clubComlumn
     * @returns {any}
     */
    isExistingClub = function(value , clubComlumn) {
        
        var clubData = $("#clubTable").jqGrid("getCol", "clubName")

        if (clubData.includes(value)) {
            return [false, " Club exising, please try again"]
        }
        return validateWordAndNumber(value, clubComlumn)
    }
    
    /**
     * To validate the player information
     * Player information: (format: text box, length 40, only allow number, a-z, A-Z, white space, required field)
     * @date 2022-04-15
     * @param {any} value
     * @param {any} columnname
     * @returns {any}
     */
     validateWordAndNumber = function (value, columnname) {
        var inputRGEX = /^[a-zA-Z_ ]*$/;
        var inputResult = inputRGEX.test(value);

        if (value.length < 5) {
            return [false, columnname + " is very short, please try again"]
        }

        if (value.charAt(0) !== value.charAt(0).toUpperCase()){
            return [false, value + ": the first letter must be capitalized"]
        }

        if (!(inputResult)) {
            return [false, columnname + " field contains must be only words "];
        } else {
            return [true, ""];
        }
    }

    /**
     * Check if a date is valid
     * @date 2022-04-25
     * @param {any} value
     * @param {any} columnname
     * @returns {any}
     */
    isvalidDate = function (value, columnname) {

        if (!/^\d{1,2}\/\d{1,2}\/\d{4}$/.test(value)) {
            return [false, "Your Date is wrong fomat, please type again"];
        }
        if (parseInt(value.substring(0, 2)) > 32 || parseInt(value.substring(0, 2)) < 0) {
            return [false, "Your day " + value.substring(0, 2) +  " seem to be wrong fomat, please type again"];
        }
        if (parseInt(value.substring(3, 5)) > 12 || parseInt(value.substring(3, 5)) < 0) {
            return [false, "Your month " + value.substring(3, 5) + " seem to be wrong fomat, please type again"];
        }
        const currentYear = new Date().getFullYear();
        if (parseInt(value.substring(6)) > currentYear) {
            return [false, "Your year " + value.substring(6) + " seem to be wrong fomat, please type again"];
        }
        
        return [true, ""];
    }

    /**
     * Check if a abbrev is valid
     * @date 2022-04-25
     * @param {any} value
     * @param {any} columnname
     * @returns {any}
     */
    isvalidAbbrev = function (value, columnname) {

        var inputRGEX = /^[a-zA-Z_ ]*$/;
        var inputResult = inputRGEX.test(value);

        if (value.charAt(0) !== value.charAt(0).toUpperCase()){
            return [false, value + ": the first letter must be capitalized"]
        }
        
        if (!(inputResult)) {
            return [false, columnname + " field contains must be only words "];
        } else {
            return [true, ""];
        }
    }

    /**
     * Check the height is typed correctly
     * @date 2022-04-25
     * @param {any} value
     * @param {any} columnname
     * @returns {any}
     */
    isvalidHeight = function (value, columnname) {

        var inputRGEX = /^[0-9_ ]*$/;
        var inputResult = inputRGEX.test(value);

        if (value < 100) {
            return [false, columnname + ": Looks like your height is wrong, please try again !"];
        }
        if (!(inputResult)) {
            return [false, columnname + " field contains must be only numbers "];
        } else {
            return [true, ""];
        }
    }

    /**
     * to create the select element for the selection
     * @date 2022-04-25
     * @param {any} e
     * @returns {any}
     */
    select2 = function (e) {
        $(e).select2({
            selectOnClose: true,
            width: '100%',
        });
    }
}