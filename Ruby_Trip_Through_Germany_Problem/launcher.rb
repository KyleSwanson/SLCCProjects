require_relative 'data/lamedb'
require_relative 'data/place'
require_relative 'data/connection'
require_relative 'trip'
require_relative 'display'

require 'logger'
#require 'active_record'
#require 'rake'

class Launcher
        def initialize(*args)
        end
        
        def start()
            db = Database.new

            entries = Place.entry_cities

            time_trips = []

            entries.each do |entry| 
                the_trip = Trip.new(entry, "time")
                the_trip.start()

                time_trips << the_trip
            end

            cost_trips = []

            entries.each do |entry| 
                the_trip = Trip.new(entry, "cost")
                the_trip.start()

                cost_trips << the_trip
            end


            # trips.each do |trip|
            #     Display.display_terminal(trip.info)
            #     Display.create_html(trip.info)
            # end
            puts
            puts "Quickest Trip"
            puts
            quickest = Trip.quickest_trip(time_trips)
            Display.display_terminal(quickest.info)

            puts
            puts "Cheapest Trip"
            puts

            cheapest = Trip.cheapest_trip(cost_trips)
            Display.display_terminal(cheapest.info)


            puts
            puts "The Bottom Line"
            puts
            Display.display_bottom_line_terminal(quickest, "The Quickest Trip")
            puts
            puts "_________________________________________________________"
            puts
            Display.display_bottom_line_terminal(cheapest, "The Cheapest Trip")
        end
end



if __FILE__ == $PROGRAM_NAME
        puts "Starting Kyle's Trip Through Germany"
        prg = Launcher.new
        prg.start()
end



