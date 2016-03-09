var __extends = (this && this.__extends) || function (d, b) {
    for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p];
    function __() { this.constructor = d; }
    d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
};
var MapManagement;
(function (MapManagement) {
    var POICategoryEnum;
    (function (POICategoryEnum) {
        POICategoryEnum[POICategoryEnum["Unknown"] = 0] = "Unknown";
        POICategoryEnum[POICategoryEnum["Construction"] = 1] = "Construction";
        POICategoryEnum[POICategoryEnum["Incident"] = 2] = "Incident";
        POICategoryEnum[POICategoryEnum["TrafficJam"] = 3] = "TrafficJam";
        POICategoryEnum[POICategoryEnum["LaneClosed"] = 4] = "LaneClosed";
        POICategoryEnum[POICategoryEnum["RoadClosed"] = 5] = "RoadClosed";
        POICategoryEnum[POICategoryEnum["PoliceTrap"] = 6] = "PoliceTrap";
        POICategoryEnum[POICategoryEnum["Hazard"] = 7] = "Hazard";
    })(POICategoryEnum || (POICategoryEnum = {}));
    var LeafletMapRoute = (function () {
        function LeafletMapRoute(layer, layer2, route, points) {
            this.layer = layer;
            this.layer2 = layer2;
            this.route = route;
            this.points = points;
        }
        return LeafletMapRoute;
    })();
    var LeafletMapPOI = (function () {
        function LeafletMapPOI(marker, poi, point) {
            this.marker = marker;
            this.poi = poi;
            this.point = point;
        }
        return LeafletMapPOI;
    })();
    var MapManager = (function () {
        function MapManager(mapElementId) {
            this.mapElementId = mapElementId;
            this.leafletMapRouteById = {};
            this.leafletMapPOIById = {};
            this.markerCluster = null;
            this.initialize();
        }
        MapManager.prototype.initialize = function () {
            this.map = L.map(this.mapElementId);
            this.map.setView([51.1072199, 3.7676438], 11);
            var googleLayer = new L.Google('ROADMAP');
            this.map.addLayer(googleLayer, true);
        };
        MapManager.prototype.showRoute = function (r) {
            var llmr;
            if (!this.leafletMapRouteById[r.id]) {
                var latLngs = MapManager.convertWaypointsToLatLng(r.waypoints);
                var color = MapManager.getColor(r.currentDelay, false);
                var colordark = MapManager.getColor(r.currentDelay, true);
                var path = L.polyline(latLngs, { stroke: true, weight: 5, color: color, opacity: 1 });
                var path2 = L.polyline(latLngs, { stroke: true, weight: 3, color: colordark, opacity: 1, className: "animated-polyline" });
                this.initializePathPopup(path, r);
                this.initializePathPopup(path2, r);
                this.map.addLayer(path, false);
                this.map.addLayer(path2, false);
                llmr = new LeafletMapRoute(path, path2, r, latLngs);
                this.leafletMapRouteById[r.id] = llmr;
            }
            else {
                // already exists, update layer
                llmr = this.leafletMapRouteById[r.id];
                llmr.layer.setStyle({ fillColor: MapManager.getColor(r.currentDelay, false) });
                llmr.layer2.setStyle({ fillColor: MapManager.getColor(r.currentDelay, true) });
                llmr.layer.redraw();
                llmr.layer2.redraw();
            }
        };
        MapManager.prototype.showPOIs = function (pois) {
            this.markerCluster = L.markerClusterGroup({ disableClusteringAtZoom: 13 });
            for (var _i = 0; _i < pois.length; _i++) {
                var p = pois[_i];
                this.showPOI(p);
            }
            this.map.addLayer(this.markerCluster);
        };
        MapManager.prototype.showPOI = function (p) {
            var llmp;
            if (!this.leafletMapPOIById[p.id]) {
                var latLng = new L.LatLng(p.latitude, p.longitude);
                var color;
                var iconUrl = MAIN_ROOT + "/static/images/poi_";
                switch (p.category) {
                    case POICategoryEnum.Incident:
                        color = "blue";
                        iconUrl += "incident.png";
                        break;
                    case POICategoryEnum.Construction:
                        color = "yellow";
                        iconUrl += "construction.png";
                        break;
                    case POICategoryEnum.TrafficJam:
                        color = "red";
                        iconUrl += "jam.png";
                        break;
                    case POICategoryEnum.LaneClosed:
                        color = "red";
                        iconUrl += "laneclosed.png";
                        break;
                    case POICategoryEnum.RoadClosed:
                        color = "red";
                        iconUrl += "roadclosed.png";
                        break;
                    case POICategoryEnum.PoliceTrap:
                        color = "red";
                        iconUrl += "police.png";
                        break;
                    case POICategoryEnum.Hazard:
                        color = "red";
                        iconUrl += "hazard.png";
                        break;
                    case POICategoryEnum.Unknown:
                        color = "black";
                        iconUrl += "unknown.png";
                        break;
                }
                var icon = L.icon({ iconSize: new L.Point(24, 24), iconUrl: iconUrl });
                var marker = L.marker(latLng, { icon: icon, clickable: true });
                this.markerCluster.addLayer(marker);
                this.initializePOIPopup(marker, p);
                llmp = new LeafletMapPOI(marker, p, latLng);
                this.leafletMapRouteById[p.id] = llmp;
            }
            else {
                // already exists, update layer
                llmp = this.leafletMapRouteById[p.id];
                llmp.marker.update();
            }
        };
        MapManager.prototype.initializePOIPopup = function (marker, poi) {
            marker.bindPopup("\n                " + poi.info + "\n                <br/>\n                Since: " + poi.since + "\n                <br/>\n                Provided by: " + poi.source + "\n            ", {});
        };
        MapManager.prototype.centerMap = function () {
            var allPoints = [];
            for (var id in this.leafletMapRouteById) {
                for (var _i = 0, _a = this.leafletMapRouteById[id].points; _i < _a.length; _i++) {
                    var p = _a[_i];
                    allPoints.push(p);
                }
            }
            var bounds = new L.LatLngBounds(allPoints);
            this.map.fitBounds(bounds, {
                animate: false,
                zoom: { animate: false },
                pan: { animate: false }
            });
        };
        MapManager.prototype.centerMapOnRoute = function (id) {
            var llmr = this.leafletMapRouteById[id];
            if (llmr) {
                var bounds = new L.LatLngBounds(llmr.points);
                this.map.fitBounds(bounds, {});
            }
        };
        MapManager.prototype.initializePathPopup = function (path, route) {
            path.bindPopup("\n                " + route.name + " (" + route.distance + "m)\n                <br>\n                  <span class=\"label label-info time\" data-time=\"" + route.averageCurrentTravelTime + "\">\n                      " + route.averageCurrentTravelTime + "\n                  </span>                  \n                  <span class=\"label time label-delay\" data-time=\"" + route.currentDelay + "\">\n                    " + route.currentDelay + "\n                  </span>\n                  <div class=\"pull-right\">\n                        <a href='" + MAIN_ROOT + "/route/detail/" + route.id + "'>Detail</a>\n                  </div>\n            ", {});
            path.on("popupopen", function () {
                // todo beter afhandelen
                window.labelDelays();
                window.formatTimes();
            });
        };
        MapManager.getColor = function (delay, dark) {
            var level = window.getDelayLevel(delay);
            // spijtig genoeg zijn paths met svg en kunnen er geen css klassen gebruikt worden
            if (level == 0)
                return dark ? "#306e30" : "#5cb85c";
            else if (level == 1)
                return dark ? "#df8a13" : "#f0ad4e";
            else if (level == 2)
                return dark ? "#b52b27" : "#d9534f";
            return "#5cb85c";
        };
        MapManager.convertWaypointsToLatLng = function (waypoints) {
            var latlngs = [];
            for (var _i = 0; _i < waypoints.length; _i++) {
                var wp = waypoints[_i];
                latlngs.push(L.latLng(wp.latitude, wp.longitude));
            }
            return latlngs;
        };
        MapManager.prototype.setRouteVisibility = function (id, visible) {
            var path = this.leafletMapRouteById[id].layer;
            var path2 = this.leafletMapRouteById[id].layer2;
            // great Open source (TM): https://github.com/Leaflet/Leaflet/issues/2662
            var element = path._path;
            var element2 = path2._path;
            if (visible) {
                $(element).show();
                $(element2).show();
            }
            else {
                $(element).hide();
                $(element2).hide();
            }
        };
        return MapManager;
    })();
    var RemoteRouteManager = (function (_super) {
        __extends(RemoteRouteManager, _super);
        function RemoteRouteManager(mapElementId, mapRouteUrl) {
            _super.call(this, mapElementId);
            this.mapRouteUrl = mapRouteUrl;
        }
        RemoteRouteManager.prototype.updateRoutes = function () {
            var _this = this;
            $.ajax(this.mapRouteUrl, {
                method: "GET",
                dataType: "json",
                success: function (data) {
                    _this.showRoutes(data);
                    _this.showPOIs(data.pois);
                    _this.centerMap();
                }
            });
        };
        RemoteRouteManager.prototype.showRoutes = function (data) {
            for (var _i = 0, _a = data.routes; _i < _a.length; _i++) {
                var r = _a[_i];
                this.showRoute(r);
            }
        };
        return RemoteRouteManager;
    })(MapManager);
    function intializeRouteMap(elementId, ajaxUrl) {
        var mgr = new RemoteRouteManager(elementId, ajaxUrl);
        mgr.updateRoutes();
        return mgr;
    }
    MapManagement.intializeRouteMap = intializeRouteMap;
})(MapManagement || (MapManagement = {}));
