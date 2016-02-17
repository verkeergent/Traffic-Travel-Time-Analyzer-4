declare namespace L {
        class Google {
            constructor(type: string);
        }
}


namespace MapManagement {
    
    interface MapData {
        routes:MapRoute[];    
    }
    interface MapRoute {
        id:number;
        name:string;
        distance:number;
        trafficDelayPercentage:number;
        waypoints:MapWaypoint[];
    }
    interface MapWaypoint {
        latitude:number;
        longitude:number;    
    }
        
    class LeafletMapRoute {
        constructor(public layer:L.Path, public route:MapRoute) { }
    }
    
    class MapManager {
        
        private leafletMapRouteById = {};
        private map:L.Map;
        
        constructor(private mapElementId:string, private mapRouteUrl:string) {
            this.initialize();
        }
        
        private initialize() {
            this.map = L.map(this.mapElementId);
            this.map.setView([51.1072199, 3.7676438], 11);
            
            var googleLayer = new L.Google('ROADMAP');
            this.map.addLayer(<L.ILayer>googleLayer, true);
        }
        
        updateRoutes() {
            $.ajax(this.mapRouteUrl,{
            method: "GET",
            dataType: "json",
            success:(data:MapData) => {
                this.showRoutes(data);
            },
            });
        }
        
        private showRoutes(data:MapData) {
            for(let r of data.routes) {
                
                let llmr:LeafletMapRoute;
                if(!this.leafletMapRouteById[r.id]) {
                    
                    let latLngs = MapManager.convertWaypointsToLatLng(r.waypoints);
                    
                    let color = MapManager.getColorFromTrafficDelayPercentage(r.trafficDelayPercentage);
                    
                    let path = L.polyline(latLngs, { color: color, });
                    
                    this.initializePathPopup(path, r);
                    
                    this.map.addLayer(path, false);
                    llmr = new LeafletMapRoute(path, r);
                    this.leafletMapRouteById[r.id] = llmr;
                }
                else {
                    // already exists, update layer
                    llmr = this.leafletMapRouteById[r.id];
                    // TODO change color somehow
                }
            }
        }
        
        private initializePathPopup(path:L.Path, route:MapRoute) {
            path.bindPopup(`${route.name} (${route.distance}m)`, { });
        }
        
        private static getColorFromTrafficDelayPercentage(delayPercentage:number):string {
            if(delayPercentage >= 0 && delayPercentage < 0.10)
                return "green";
            else if(delayPercentage >= 0.1 && delayPercentage < 0.30)
                return "yellow";
            else if(delayPercentage >= 0.3 && delayPercentage < 0.60)
                return "orange";
            else if(delayPercentage >= 0.6 && delayPercentage < 0.90)
                return "red";
            else if(delayPercentage >= 0.9)
                return "brown";
                
            return "green";
        }
        
        private static convertWaypointsToLatLng(waypoints:MapWaypoint[]):L.LatLng[] {
            let latlngs:L.LatLng[] = [];
            for(let wp of waypoints) {
                latlngs.push(L.latLng(wp.latitude, wp.longitude));
            }
            return latlngs;
        }
    }
    
    
    export function intializeMap(elementId:string, ajaxUrl:string) {
        let mgr = new MapManager(elementId, ajaxUrl);
        mgr.updateRoutes();
    }
}