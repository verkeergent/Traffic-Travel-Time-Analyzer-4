(function (trajectDetail, verkeer, routeChart, $, moment) {
    // view only variables
    var refreshIcon;
    var updateBtn;
    var toggleBtn;
    var datePickerBegin;
    var datePickerEnd;
    var toggled = false;
    var routeDataFinished = false, trafficDataFinished = false;

    // data vars
    var id;
    const chartId = "container";
    var travelTimes;
    var delays;

    /**
     *  display settings for the chart
     */
    var providerSettings = {
        Coyote: {color: "#7cb5ec", symbol: "circle"},
        BeMobile: {color: "#434348", symbol: "diamond"},
        ViaMichelin: {color: "#90ed7d", symbol: "square"},
        HereMaps: {color: "#f7a35c", symbol: "triangle"},
        Bing: {color: "#8085e9", symbol: "triangle-down"},
        Waze: {color: "#f15c80", symbol: "circle"},
        TomTom: {color: "#e4d354", symbol: "diamond"},
        GoogleMaps: {color: "#2b908f", symbol: "square"}
    };

    // weather data
    const weatherConditions = {
        1: {discription: "Light Drizzle"},
        2: {discription: "Heavy Drizzle"},
        3: {discription: "Light Rain"},
        4: {discription: "Heavy Rain"},
        5: {discription: "Light Snow"},
        6: {discription: "Heavy Snow"},
        7: {discription: "Light Snow Grains"},
        8: {discription: "Heavy Snow Grains"},
        9: {discription: "Light Ice Crystals"},
        10: {discription: "Heavy Ice Crystals"},
        11: {discription: "Light Ice Pellets"},
        12: {discription: "Heavy Ice Pellets"},
        13: {discription: "Light Hail"},
        14: {discription: "Heavy Hail"},
        15: {discription: "Light Mist"},
        16: {discription: "Heavy Mist"},
        17: {discription: "Light Fog"},
        18: {discription: "Heavy Fog"},
        19: {discription: "Light Fog Patches"},
        20: {discription: "Heavy Fog Patches"},
        21: {discription: "Light Smoke"},
        22: {discription: "Heavy Smoke"},
        23: {discription: "Light Volcanic Ash"},
        24: {discription: "Heavy Volcanic Ash"},
        25: {discription: "Light Widespread Dust"},
        26: {discription: "Heavy Widespread Dust"},
        27: {discription: "Light Sand"},
        28: {discription: "Heavy Sand"},
        29: {discription: "Light Haze"},
        30: {discription: "Heavy Haze"},
        31: {discription: "Light Spray"},
        32: {discription: "Heavy Spray"},
        33: {discription: "Light Dust Whirls"},
        34: {discription: "Heavy Dust Whirls"},
        35: {discription: "Light Sandstorm"},
        36: {discription: "Heavy Sandstorm"},
        37: {discription: "Light Low Drifting Snow"},
        38: {discription: "Heavy Low Drifting Snow"},
        39: {discription: "Light Low Drifting Widespread Dust"},
        40: {discription: "Heavy Low Drifting Widespread Dust"},
        41: {discription: "Light Low Drifting Sand"},
        42: {discription: "Heavy Low Drifting Sand"},
        43: {discription: "Light Blowing Snow"},
        44: {discription: "Heavy Blowing Snow"},
        45: {discription: "Light Blowing Widespread Dust"},
        46: {discription: "Heavy Blowing Widespread Dust"},
        47: {discription: "Light Blowing Sand"},
        48: {discription: "Heavy Blowing Sand"},
        49: {discription: "Light Rain Mist"},
        50: {discription: "Heavy Rain Mist"},
        51: {discription: "Light Rain Showers"},
        52: {discription: "Heavy Rain Showers"},
        53: {discription: "Light Snow Showers"},
        54: {discription: "Heavy Snow Showers"},
        55: {discription: "Light Snow Blowing Snow Mist"},
        56: {discription: "Heavy Snow Blowing Snow Mist"},
        57: {discription: "Light Ice Pellet Showers"},
        58: {discription: "Heavy Ice Pellet Showers"},
        59: {discription: "Light Hail Showers"},
        60: {discription: "Heavy Hail Showers"},
        61: {discription: "Light Small Hail Showers"},
        62: {discription: "Heavy Small Hail Showers"},
        63: {discription: "Light Thunderstorm"},
        64: {discription: "Heavy Thunderstorm"},
        65: {discription: "Light Thunderstorms and Rain"},
        66: {discription: "Heavy Thunderstorms and Rain"},
        67: {discription: "Light Thunderstorms and Snow"},
        68: {discription: "Heavy Thunderstorms and Snow"},
        69: {discription: "Light Thunderstorms and Ice Pellets"},
        70: {discription: "Heavy Thunderstorms and Ice Pellets"},
        71: {discription: "Light Thunderstorms with Hail"},
        72: {discription: "Heavy Thunderstorms with Hail"},
        73: {discription: "Light Thunderstorms with Small Hail"},
        74: {discription: "Heavy Thunderstorms with Small Hail"},
        75: {discription: "Light Freezing Drizzle"},
        76: {discription: "Heavy Freezing Drizzle"},
        77: {discription: "Light Freezing Rain"},
        78: {discription: "Heavy Freezing Rain"},
        79: {discription: "Light Freezing Fog"},
        80: {discription: "Heavy Freezing Fog"},
        81: {discription: "Patchesof Fog"},
        82: {discription: "Shallow Fog"},
        83: {discription: "Partial Fog"},
        84: {discription: "Overcast"},
        85: {discription: "Clear"},
        86: {discription: "Partly Cloudy"},
        87: {discription: "Mostly Cloudy"},
        88: {discription: "Scattered Clouds"},
        89: {discription: "Small Hail"},
        90: {discription: "Squalls"},
        91: {discription: "Funnel Cloud"},
        92: {discription: "Unknown Precipitation"},
        93: {discription: "Unknown"}
    };

    $(document).ready(function () {
        // init buttons and other vars
        refreshIcon = document.getElementById("refresh-icon");
        updateBtn = document.getElementById("update-btn");
        toggleBtn = document.getElementById("toggle-btn");
        datePickerBegin = $("#datetimepicker-begin");
        datePickerEnd = $("#datetimepicker-end");
        id = $("#routeId").val();

        // set default date in view
        datePickerBegin.datetimepicker({
            format: "DD/MM/YYYY HH:mm",
            showTodayButton: true,
            showClear: true,
            defaultDate: moment().startOf("day")
        });
        datePickerEnd.datetimepicker({
            format: "DD/MM/YYYY HH:mm",
            showTodayButton: true,
            showClear: true,
            defaultDate: moment().endOf("day")
        });

        // set button actions
        updateBtn.addEventListener("click", trajectDetail.getRouteData);
        toggleBtn.addEventListener("click", trajectDetail.toggleChart);

        // build the empty chart and fetch the data
        routeChart.buildChart(chartId);
        trajectDetail.getRouteData();
    });

    /**
     * Downloads the data from the server
     */
    trajectDetail.getRouteData = function () {
        // spin the update button
        refreshIcon.classList.add("spinning");
        var startDate = datePickerBegin.data("DateTimePicker").date().toDate();
        var endDate = datePickerEnd.data("DateTimePicker").date().toDate();

        // fetch traveltime and delay data
        $.ajax({
            method: "GET",
            url: "../routedata",
            data: {
                id: id,
                startDate: startDate,
                endDate: endDate
            },
            success: function (data) {
                toggled = false;
                travelTimes = data.travelTimes;
                delays = data.delayData;
                routeChart.showDefaultTitle();
                trajectDetail.setSeriesViewSettings(travelTimes);
                trajectDetail.setSeriesViewSettings(delays);
                routeChart.setChartData(travelTimes);
            },
            complete: function () {
                routeDataFinished = true;
                trajectDetail.stopSpinning();
            }
        });

        // fetch traffic data
        $.ajax({
            method: "GET",
            url: "../trafficdata",
            data: {
                id: id,
                startDate: startDate,
                endDate: endDate
            },
            success: function (data) {
                trajectDetail.buildTrafficJamTable(data);
            },
            complete: function () {
                trafficDataFinished = true;
                trajectDetail.stopSpinning();
            }
        });
    };

    /**
     * Stops the update button from spinning
     */
    trajectDetail.stopSpinning = function () {
        if(routeDataFinished && trafficDataFinished){
            refreshIcon.classList.remove("spinning");
            routeDataFinished = false;
            trafficDataFinished = false;
        }
    };

    /**
     * Adds the view settings for a certain provider to the data
     * @param providersData
     */
    trajectDetail.setSeriesViewSettings = function (providersData) {
        providersData.forEach(function (ele) {
            var setting = providerSettings[ele.name];
            var color = setting ? setting.color : null;
            var symbol = setting ? setting.symbol : null;
            ele.color = color;
            ele.marker = {
                symbol: symbol
            };
        });
    };

    /**
     * Toggles the char between delay and traveltime
     */
    trajectDetail.toggleChart = function () {
        if (toggled) {
            routeChart.showDefaultTitle();
            routeChart.setChartData(travelTimes);
        } else {
            routeChart.showToggleTitle();
            routeChart.setChartData(delays);
        }
        toggled = !toggled;
    };

    /**
     * Builds a table for the traffic jams
     * @param jams
     */
    trajectDetail.buildTrafficJamTable = function (jams) {
        var table = document.getElementById("tblJamsBody");

        var html = "";
        for (var i = 0; i < jams.length; i++) {
            var jam = jams[i];
            var jamRow = "<tr>";

            jamRow += "<td>" + moment(jam.trafficJam.from).format("DD/MM/YYYY HH:mm:ss") + "</td>";
            jamRow += "<td>" + moment(jam.trafficJam.to).format("DD/MM/YYYY HH:mm:ss") + "</td>";

            var duration = moment.utc(moment(jam.trafficJam.to).diff(moment(jam.trafficJam.from))).format("HH:mm:ss");
            jamRow += "<td>" + duration + "</td>";

            jamRow += "<td>" + verkeer.secondsToText(jam.trafficJam.avgDelay) + "</td>";
            jamRow += "<td>" + verkeer.secondsToText(jam.trafficJam.maxDelay) + "</td>";

            // TODO possible causes
            jamRow += "<td>";

            if (jam.causes !== null) {
                var causes = jam.causes.sort(function (a, b) {
                    return b.avgProbability - a.avgProbability;
                });
                for (var j = 0; j < causes.length; j++) {
                    var icon = trajectDetail.getIconForJamCause(causes[j]);
                    if (icon !== "") {
                        jamRow += "<img src='" + icon + "'/>";
                    }

                    if (jam.causes[j].description === null && jam.causes[j].category === "Weather") {
                        jamRow += weatherConditions[jam.causes[j].subCategory].discription;
                    }
                    else {
                        jamRow += jam.causes[j].description;
                    }

                    jamRow += " " + (causes[j].avgProbability * 100).toFixed(2) + "%";
                    if (j !== jam.causes.length - 1)
                        jamRow += "<br/>";
                }
            }

            jamRow += "</td>";

            jamRow += "</tr>";
            html += jamRow;
        }

        html += "";

        table.innerHTML = html;
    };

    trajectDetail.getIconForJamCause = function (cause) {
        var iconUrl = "";
        if (cause.category === "POI") { // POI
            iconUrl = verkeer.MAIN_ROOT + "/static/images/poi_";
            switch (cause.subCategory) {
                case 0: //Unknown(0),
                    iconUrl += "unknown";
                    break;
                case 1: //Construction(1),
                    iconUrl += "construction";
                    break;
                case 2: //Incident(2),
                    iconUrl += "incident";
                    break;
                case 3: //TrafficJam(3),
                    iconUrl += "jam";
                    break;
                case 4: //LaneClosed(4),
                    iconUrl += "laneclosed";
                    break;
                case 5: //RoadClosed(5),
                    iconUrl += "roadclosed";
                    break;
                case 6: //PoliceTrap(6),
                    iconUrl += "police";
                    break;
                case 7: //Hazard(7),
                    iconUrl += "hazard";
                    break;
                case 8: //Accident(8);
                    iconUrl += "accident";
                    break;
            }
        } else {
            iconUrl = verkeer.MAIN_ROOT + "/static/images/";
            switch (cause.subCategory) {
                case 4, 13, 14, 49, 50:
                    iconUrl += "rain";
                    break;
                case 5 - 12, 43, 44:
                    iconUrl += "snow";
                    break;
                case 15 - 20:
                    iconUrl += "fog";
                    break;
                case 63 - 68, 85:
                    iconUrl += "tstorms";
                    break;
            }
        }

        return iconUrl += ".png";
    };
}(window.verkeer.trajectDetail = window.verkeer.trajectDetail || {}, verkeer, verkeer.routeChart, jQuery, moment));