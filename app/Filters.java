import com.google.inject.Inject;
import com.google.inject.Singleton;

import filters.ResponseCodeMonitorFilter;
import play.*;
import play.mvc.EssentialFilter;
import play.http.HttpFilters;

import filters.CorsFilter;

/**
 * This class configures filters that run on every request. This
 * class is queried by Play to get a list of filters.
 *
 * Play will automatically use filters from any class called
 * <code>Filters</code> that is placed the root package. You can load filters
 * from a different class by adding a `play.http.filters` setting to
 * the <code>application.conf</code> configuration file.
 */
@Singleton
public class Filters implements HttpFilters {

    private final Environment env;
    private final EssentialFilter corsFilter;
    private final ResponseCodeMonitorFilter responseCodeMonitorFilter;

    /**
     * @param env Basic environment settings for the current application.
     * @param corsFilter A demonstration filter that adds a header to
     */
    @Inject
    public Filters(Environment env, CorsFilter corsFilter, ResponseCodeMonitorFilter responseCodeMonitorFilter) {
        this.env = env;
        this.corsFilter = corsFilter;
        this.responseCodeMonitorFilter = responseCodeMonitorFilter;
    }

    @Override
    public EssentialFilter[] filters() {
        if(env.mode().equals(Mode.PROD)){ //if in prod mode, allow response code monitoring
            return new EssentialFilter[] { responseCodeMonitorFilter};
        } else if (env.mode().equals(Mode.TEST)) { //If in test mode, allow CORS
          return new EssentialFilter[] { corsFilter };
      } else {
          return new EssentialFilter[] { };
      }
    }

}
