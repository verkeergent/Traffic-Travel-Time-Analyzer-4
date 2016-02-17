var MapManagement;
(function (MapManagement) {
    var LeafletMapRoute = (function () {
        function LeafletMapRoute(layer, route) {
            this.layer = layer;
            this.route = route;
        }
        return LeafletMapRoute;
    })();
    var MapManager = (function () {
        function MapManager(mapElementId, mapRouteUrl) {
            this.mapElementId = mapElementId;
            this.mapRouteUrl = mapRouteUrl;
            this.leafletMapRouteById = {};
            this.initialize();
        }
        MapManager.prototype.initialize = function () {
            this.map = L.map(this.mapElementId);
            this.map.setView([51.1072199, 3.7676438], 11);
            var googleLayer = new L.Google('ROADMAP');
            this.map.addLayer(googleLayer, true);
        };
        MapManager.prototype.updateRoutes = function () {
            var _this = this;
            $.ajax(this.mapRouteUrl, {
                method: "GET",
                dataType: "json",
                success: function (data) {
                    _this.showRoutes(data);
                }
            });
        };
        MapManager.prototype.showRoutes = function (data) {
            for (var _i = 0, _a = data.routes; _i < _a.length; _i++) {
                var r = _a[_i];
                var llmr = void 0;
                if (!this.leafletMapRouteById[r.id]) {
                    var latLngs = MapManager.convertWaypointsToLatLng(r.waypoints);
                    var color = MapManager.getColorFromTrafficDelayPercentage(r.trafficDelayPercentage);
                    var path = L.polyline(latLngs, { color: color });
                    this.initializePathPopup(path, r);
                    this.map.addLayer(path, false);
                    llmr = new LeafletMapRoute(path, r);
                    this.leafletMapRouteById[r.id] = llmr;
                }
                else {
                    // already exists, update layer
                    llmr = this.leafletMapRouteById[r.id];
                }
            }
        };
        MapManager.prototype.initializePathPopup = function (path, route) {
            path.bindPopup(route.name + " (" + route.distance + "m)", {});
        };
        MapManager.getColorFromTrafficDelayPercentage = function (delayPercentage) {
            if (delayPercentage >= 0 && delayPercentage < 0.10)
                return "green";
            else if (delayPercentage >= 0.1 && delayPercentage < 0.30)
                return "yellow";
            else if (delayPercentage >= 0.3 && delayPercentage < 0.60)
                return "orange";
            else if (delayPercentage >= 0.6 && delayPercentage < 0.90)
                return "red";
            else if (delayPercentage >= 0.9)
                return "brown";
            return "green";
        };
        MapManager.convertWaypointsToLatLng = function (waypoints) {
            var latlngs = [];
            for (var _i = 0; _i < waypoints.length; _i++) {
                var wp = waypoints[_i];
                latlngs.push(L.latLng(wp.latitude, wp.longitude));
            }
            return latlngs;
        };
        return MapManager;
    })();
    function intializeMap(elementId, ajaxUrl) {
        var mgr = new MapManager(elementId, ajaxUrl);
        mgr.updateRoutes();
    }
    MapManagement.intializeMap = intializeMap;
})(MapManagement || (MapManagement = {}));
