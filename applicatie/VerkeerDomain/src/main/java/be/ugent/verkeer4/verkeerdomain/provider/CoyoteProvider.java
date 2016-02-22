package be.ugent.verkeer4.verkeerdomain.provider;

import be.ugent.verkeer4.verkeerdomain.Settings;
import be.ugent.verkeer4.verkeerdomain.data.ProviderEnum;
import be.ugent.verkeer4.verkeerdomain.data.Route;
import be.ugent.verkeer4.verkeerdomain.data.RouteData;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CoyoteProvider implements ISummaryProvider {

    public CoyoteProvider() {

    }

    protected List<RouteData> scrape() throws InterruptedException, IOException, Exception {
        File baseDir = new File(Settings.getInstance().getScrapePath());

        File script = new File(baseDir, "coyote.pl");

        ProcessBuilder pb = new ProcessBuilder("perl", script.toPath().toString());

        pb.directory(baseDir);
        java.lang.Process p = pb.start();
        int errCode = p.waitFor();

        if (errCode == 0) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

            String headers = reader.readLine();
            String line;
            
            List<RouteData> lst = new ArrayList<>();
            
            while ((line = reader.readLine()) != null) {

                String[] parts = line.split(";");

                int totalDistance = Integer.parseInt(parts[0]);
                int totalTimeSeconds = Integer.parseInt(parts[1]);
                int totalDelaySeconds = Integer.parseInt(parts[2]);
                String name = parts[3];

                Route r = getRouteByCoyoteName(name);
                if (r != null) {
                    RouteData rd = new RouteData();
                    rd.setProvider(ProviderEnum.Coyote);
                    rd.setTimestamp(new Date());
                    rd.setTravelTime(totalTimeSeconds);
                    rd.setDelay(totalDelaySeconds);
                    rd.setTotalDistance(totalDistance);
                    rd.setRouteId(r.getId());
                    lst.add(rd);
                }
            }
            
            return lst;
        }

        return null;
    }

    private Route getRouteByCoyoteName(String name) {
        return null; // TODO!
    }

    @Override
    public List<RouteData> poll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
