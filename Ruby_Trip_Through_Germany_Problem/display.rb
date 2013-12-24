# encoding: UTF-8

class Display
	# def info
 #        {
 #        	:fuel_cost=>@car_cost, 
 #        	:car_time=>@car_time, 
 #        	:car_cost=>rental_cost(@car_time), 
 #        	:train_time=>@train_time, 
 #        	:train_cost=>@train_cost,
 #        	:total_time=>total_time,
 #        	:total_expenses=>total_expenses,
 # 			:stages=>stages
 #        }
 #    end

	def self.display_terminal(info)
		print self.title_to_s("Starting in " + info[:stages][0].town.name)
		print self.stages_to_s(info)
		puts
		print self.costs_to_s(info)
		puts
		print self.time_to_s(info)
		self.break

	end

	def self.display_bottom_line_terminal(trip, title)
		print self.title_to_s(title)
		start_town = trip.info[:stages][0].town.name
		print "Start with the trip from: #{start_town}\n"
		print self.costs_to_s(trip.info)
		puts
		print self.time_to_s(trip.info)
	end

	def self.title_to_s(trip_name)
		"-=-=-=-= #{trip_name} =-=-=-=-\n"
	end

	def self.stages_to_s(info)
		stages = ""

		info[:stages].each do |s|
			if s.form == nil then 
				stages += "Starting Journey in #{s.town.name}\n"
			else
				stages += "#{silly_vernacular_going}#{s.town.name} in a #{s.form}\n" 
			end
		end

		stages
	end

	def self.costs_to_s(info)
		costs = self.title_to_s("Expenses")
		costs += "Train Costs: €#{info[:train_cost].round(2)}\n"
		costs += "Car Costs: €#{info[:car_cost].round(2)}\n"
		costs += "Fuel Costs: €#{info[:fuel_cost].round(2)}\n"
		costs += "Air Fare: €#{info[:air_fare].round(2)}\n"
		costs += "Misc Costs: €#{info[:misc_cost].round(2)}\n"
		costs += "Total Costs: €#{info[:total_cost].round(2)}\n"

		costs
	end

	def self.time_to_s(info)
		time = self.title_to_s("Time")
		time += "Time on Trains: #{nice_time(info[:train_time])}\n"
		time += "Time in Cars: #{nice_time(info[:car_time])}, distance #{info[:car_distance]} km\n"
		t = min_to_time(info[:total_time])
		time += "Trip Duration: #{t[:days]} days, #{t[:hr]} hours, #{t[:min]} minutes\n"
		time += "Assuming a 8 hour stay at most locations.\n"

	end

	def self.break
		puts
		puts "-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-"
		puts
	end

	#HTML STUFF ---------------------------------------------------------

	def self.create_html(info)
		start_place = info[:stages][0].town

		hf = File.new("html_out/start_at_#{start_place.name}.html", "w+")
		
		hf.puts "<html><body>"
		hf.puts "<head><title>#{start_place.name}</title>#{self.css}</head>"
		hf.puts "<h1>Starting at #{start_place.name}</h1>"
		hf.puts "<h3>Route</h3>"

		info[:stages].each do |s|
			if s.form == nil then 
				hf.puts "Starting Journey in #{s.town.name}<br>"
			else
				hf.puts "#{silly_vernacular_going}#{s.town.name} in a #{s.form}<br>" 
			end
		end

		hf.puts "</body></html>"

		hf.close
	end

	private

	def self.css 
		css = "<style type='text/css'>\n"
		css += "body { font-family:Helvetica }"
		css += ""
		css += "</style>"
	end

	def self.nice_time(minutes)
		t = min_to_time(minutes)

		out = "#{t[:hr]} hours, #{t[:min]} minutes"
		if t[:days] != 0 then out.prepend("#{t[:days]} days, ") end

		out
	end

	def self.min_to_time(minutes)
		seconds = minutes * 60

  		mm, ss = seconds.divmod(60)            
 		hh, mm = mm.divmod(60)           
  		dd, hh = hh.divmod(24) 
  
  		{:hr => hh, :min => mm, :sec => ss.round(0), :days => dd}
	end

	def self.silly_vernacular_going
		silly = [
			"Departing to ", 
			"Departing for ",
			"Sending off to ",
			"Leaving for "
		]

		silly[rand(silly.length)]
	end
end