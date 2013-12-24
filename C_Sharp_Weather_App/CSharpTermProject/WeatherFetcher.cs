//Brady Carlson
//Kyle Swanson

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Newtonsoft.Json.Linq;
using Newtonsoft.Json;

namespace CSharpTermProject
{
    //API KEY: 4894fde6c87e4635
    //EX to get city info, http://api.wunderground.com/api/4894fde6c87e4635/geolookup/q/84105.json
    public class WeatherFetcher
    {
        public static CurrentWeather GetCurrentWeather(int zip)
        {
            string Fetched = new System.Net.WebClient().DownloadString(UrlBuilderCurrent(zip));
            JObject Json = JObject.Parse(Fetched);

            if (Json["response"]["error"] == null)
                return new CurrentWeather(Fetched, Json);
            else
                return null;
        }

        public static ThreeDayWeather GetThreeDayWeather(int zip) 
        {
            string Fetched = new System.Net.WebClient().DownloadString(UrlBuilderForecast(zip));
            JObject Json = JObject.Parse(Fetched);

            if (Json["response"]["error"] == null)
                return new ThreeDayWeather(Fetched, Json);
            else
                return null;
        }

        private static string UrlBuilderCurrent(int zip)
        {
            return string.Format("http://api.wunderground.com/api/4894fde6c87e4635/conditions/q/{0}.json", zip);
        }

        private static string UrlBuilderForecast(int zip)
        {
            return string.Format("http://api.wunderground.com/api/4894fde6c87e4635/forecast/q/{0}.json", zip);
        }
    }

    public class ThreeDayWeather
    {
        public string Fetched { get; private set; }
        public JObject Json { get; private set; }

        public List<JToken> Periods { get; private set; }

        public ThreeDayWeather(string fetched, JObject json)
        {
            Fetched = fetched;
            Json = json;

            JToken tmp_p = Json["forecast"]["txt_forecast"]["forecastday"];
                
            Periods = tmp_p.ToList();
        }

    }

    public class CurrentWeather 
    {
        public string Fetched { get; private set; }
        public JObject Json { get; private set; }

        public double CurrentTempF { get; private set; }
        public double FeelsLikeF { get; private set; }
        public double CurrentTempC { get; private set; }
        public double FeelsLikeC { get; private set; }
        public string CityName { get; private set; }
        public string WindFeel { get; private set; }
        public double WindSpeedMph { get; private set; }
        public double DewPointF { get; private set; }
        public string ForecastURL { get; private set; }
        public double VisibilityMi { get; private set; }
        public string Weather { get; private set; }
        public string Elevation { get; private set; }

        public string ImageName { get; private set; }
        public string ImageUrl { get; private set; }
        public string LocalUrl { get; private set; }


        public CurrentWeather(string fetched, JObject json)
        {
            Fetched = fetched;
            Json = json;

            if (Json["current_observation"]["temp_f"] != null)
            {

                CurrentTempF = (double)Json["current_observation"]["temp_f"];
                FeelsLikeF = (double)Json["current_observation"]["feelslike_f"];
                CurrentTempC = (double)Json["current_observation"]["temp_c"];
                FeelsLikeC = (double)Json["current_observation"]["feelslike_c"];
                CityName = (string)Json["current_observation"]["display_location"]["full"];
                WindFeel = (string)Json["current_observation"]["wind_string"];
                WindSpeedMph = (double)Json["current_observation"]["wind_mph"];
                DewPointF = (double)Json["current_observation"]["dewpoint_f"];
                ForecastURL = (string)Json["current_observation"]["forecast_url"];
                VisibilityMi = (double)Json["current_observation"]["visibility_mi"];
                Weather = (string)Json["current_observation"]["weather"];
                Elevation = (string)Json["current_observation"]["observation_location"]["elevation"];

                ImageName = (string)Json["current_observation"]["icon"];
                ImageUrl = (string)Json["current_observation"]["icon_url"];

                string folder = Environment.GetFolderPath(Environment.SpecialFolder.ApplicationData);
                LocalUrl = System.IO.Path.Combine(folder, System.IO.Path.GetRandomFileName());
                System.Net.WebClient webClient = new System.Net.WebClient();
                webClient.DownloadFile(ImageUrl, LocalUrl);
            }
        }
    }
}
