//Brady Carlson
//Kyle Swanson

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;

using System.Threading.Tasks;

using Newtonsoft.Json.Linq;
using Newtonsoft.Json;

namespace CSharpTermProject
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        public CurrentWeather TheWeather { get; set; }
        public ThreeDayWeather Forecast { get; set; }

        public MainWindow()
        {
            InitializeComponent();
            zipbox.KeyDown += CheckKeys;


            LoadingThenSet(84105);
        }


        public void LoadingThenSet(int zip)
        {
            ClearWindow();

            Task cur_task = Task.Factory.StartNew(() => GetCurrent(zip));
            cur_task.ContinueWith(x => ZipSet(), TaskScheduler.FromCurrentSynchronizationContext());

            Task three_task = Task.Factory.StartNew<ThreeDayWeather>(() => GetThree(zip));
            three_task.ContinueWith(x => SetThreeDay(), TaskScheduler.FromCurrentSynchronizationContext());
        }

        public CurrentWeather GetCurrent(int zip)
        {
            TheWeather = WeatherFetcher.GetCurrentWeather(zip);
            return TheWeather;
        }

        public ThreeDayWeather GetThree(int zip)
        {
            Forecast = WeatherFetcher.GetThreeDayWeather(zip);
            return Forecast;
        }

        public void ZipSet()
        {
            if (TheWeather == null)
            {
                // window stays clear from above...
                title.Content = "Zip code does not exist";
                return;
            }

            title.Content = TheWeather.CityName;
            Char degreeSymbol = (Char)176;
            temp.Content = string.Format("{0} {1}F  |  {2} {3}C", TheWeather.CurrentTempF, degreeSymbol, TheWeather.CurrentTempC, degreeSymbol);

            everything.Items.Clear();
            everything.Items.Add(string.Format("Current Conditions: {0}", TheWeather.Weather));
            everything.Items.Add(string.Format("Elevation: {0}", TheWeather.Elevation));
            everything.Items.Add(string.Format("Visibility: {0} miles", TheWeather.VisibilityMi));
            everything.Items.Add(string.Format("Dew Point: {0}{1}F", TheWeather.DewPointF, degreeSymbol));
            everything.Items.Add(string.Format("Feels Like: {0:0.0}{1}F | {2:0.0}{3}C",
                TheWeather.FeelsLikeF, degreeSymbol, TheWeather.FeelsLikeC, degreeSymbol));
            everything.Items.Add(string.Format("Wind Speed: {0} mph",TheWeather.WindSpeedMph));
            everything.Items.Add(string.Format("Wind Feel: {0}",TheWeather.WindFeel));

            weather_image.Source = new BitmapImage(new Uri(TheWeather.LocalUrl));
        }

        private void SetThreeDay()
        {
            if (Forecast == null)
            {
                return;
            }

            JToken today = Forecast.Periods[0];
            JToken tomorrow = Forecast.Periods[2];
            JToken day_after = Forecast.Periods[4];

            today_label.Content = today["title"];
            tomorrow_label.Content = tomorrow["title"];
            day_after_label.Content = day_after["title"];

            today_detail.Text = (string)today["fcttext"];
            tomorrow_detail.Text = (string)tomorrow["fcttext"];
            day_after_detail.Text = (string)day_after["fcttext"];
        }

        private void view_on_wund_btn_Click(object sender, RoutedEventArgs e)
        {
            //Take the user to the webpage of their current zip.
            System.Diagnostics.Process.Start(TheWeather.ForecastURL);
        }

        private void ClearWindow()
        {
            title.Content = "";
            temp.Content = "";
            everything.Items.Clear();

            today_detail.Text = "";
            tomorrow_detail.Text = "";
            day_after_detail.Text = "";

            today_label.Content = "";
            tomorrow_label.Content = "";
            day_after_label.Content = "";

            weather_image.Source = new BitmapImage();
        }

        private void CheckKeys(object sender, System.Windows.Input.KeyEventArgs e)
        {
            if (e.Key != System.Windows.Input.Key.Enter) return;

            int theZip;

            if (int.TryParse(zipbox.Text, out theZip))
            {
                LoadingThenSet(theZip);
            }
            else
            {
                ClearWindow();
                title.Content = "Invalid Zip Code";
            }
   
        }

        private void zipbox_GotFocus(object sender, RoutedEventArgs e)
        {
            TextBox tb = (TextBox)sender;
            tb.Text = string.Empty;
            tb.GotFocus -= zipbox_GotFocus;
        }
    }
}
