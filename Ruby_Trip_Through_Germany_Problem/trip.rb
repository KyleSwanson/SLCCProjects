require_relative 'data/place'
require_relative 'data/connection'

class Trip
	attr_accessor :entry_city, :car_distance, :car_time, :car_cost, :train_time, :train_costs, :visited_towns, :town_stay, :optimize_by, :rental_cost, :air_fare

	@@car = "car"
	@@train = "train"

	@@five_pack_rail_pass = 1324

	@@time_op = "time"
	@@cost_op = "cost" 

	def initialize(ep, op_by, *args)
		@entry_city = ep
		@visited_ids = []
		@to_visit_ids = Place.all_ids
		@stages = []

		@optimize_by = op_by

		@town_stay = 0

		@misc_cost = 0
		#Planes planes planes
		#Prices provided by HipMunk. Mostly
		@air_fare = 0
		#My round trip ticket from SLC to Chicago. With a connection...
		@air_fare += 364
		#My "Parents" flight from Nashville. 2 Round trip tickets
		@air_fare += 484
		#Finally the bigone of the flight to germany from chicago... 5 Tickets. Luftansa man pretty good deal.
		@air_fare += 5975


		@car_distance = 0
		@car_time = 0
		@car_cost = 0
		@rental_cost = 0

		@train_time = 0
		@train_costs = @@five_pack_rail_pass
	end

	def start
		current_city = @entry_city
		#puts "I'm starting my journey at #{entry_city.name}"

		possible_towns = Connection.find_by_town_id(@entry_city.id)
		visit(current_city, nil, nil)

		while @to_visit_ids.length > 0 do 
			routes = Connection.find_by_town_id(current_city.id)
			next_town, route, method = optimal_town(routes, current_city)

			if next_town.nil? then break end

			#That @@car should be from optimal method.
			current_city = visit(next_town, route, method)

			#sleep(0.05)
		end
	end

	def info
        {
        	:fuel_cost=>@car_cost, 
        	:car_time=>@car_time, 
        	:car_cost=>@rental_cost, 
        	:car_distance=>@car_distance,
        	:train_time=>@train_time, 
        	:train_cost=>@train_costs,
        	:total_time=>total_time,
        	:total_cost=>total_expenses,
        	:misc_cost=>@misc_cost,
        	:stages=>@stages,
        	:air_fare=>@air_fare
        }
    end

	def visit(town, route, form)
		#puts "I'm visiting #{town.name}, in a #{form}, #{route.inspect}"
		@visited_ids << town.id
		@to_visit_ids.delete(town.id)

		if form == @@car
			@car_distance += route.car_distance
			@car_time += route.car_time
			@car_cost += gas_cost(route.car_distance)
			@rental_cost += rental_cost(route.car_time)


			@stages << Stage.new(town, route.car_time, form)
		elsif form == @@train
			@train_time += route.train_time

			@stages << Stage.new(town, route.train_time, form)
		end


		#do any items we need for the town
		@town_stay += town.stay_time
		@misc_cost += town.expenses

		#return the town you are now at. 
		town
	end


	#Some private helper methods!
	#private

	def optimal_town(routes, current)
		routes = remove_visited(routes, current)

		if routes.length == 0 then 
			puts "Stuck at: #{current.name}" 
			puts "Can't get to: #{Place.find_by_id(@to_visit_ids[0]).name}"
		end

		optimal = routes[0]
		low_method = optimal_method(routes[0])
		low_time = method_time(low_method, routes[0])
		other = routes[0].other_town(current.id)


		routes.each do |r|
			r_low_method = optimal_method(r)
			r_low_time = method_time(r_low_method, r)

			if r_low_time < low_time then 
				low_time = r_low_time
				low_method = optimal_method(r)

				other = r.other_town(current.id)
				if not @visited_ids.include?(other.id) then
				 	if @to_visit_ids.include?(other.id) then 
				 		optimal = r 
					end
				end
			end
		end

		if @to_visit_ids.include?(other.id) then
			[optimal.other_town(current.id), optimal, low_method]
		else 
			puts "Journey broken"
			nil
		end
	end

	def method_time(method, connection)
		if method == @@car
			connection.car_time
		elsif method == @@train
			connection.train_time
		end
	end

	def optimal_method(connection)
		t_time = connection.train_time
		c_time = connection.car_time

		if @optimize_by == @@cost_op and t_time != -1
			@@train
		elsif t_time != -1
			if t_time < c_time then 
				@@train 
			else
				@@car
			end
		else
			@@car
		end

	end

	def remove_visited(routes, current)
		index_to_remove = []

		routes.each_with_index do |i, index|
			other_town = i.other_town(current.id)

			if @visited_ids.include?(other_town.id)
				index_to_remove << index
			end 
		end

		index_to_remove.reverse!
		index_to_remove.each do |j|
			routes.delete_at(j)
		end

		routes
	end

	def gas_cost(distance)
		diesel_km_a_l = 13
		petrol_km_a_l = 22

		diesel_per_l = 1.70
		petrol_per_l = 1.76

		l_used = (distance / diesel_km_a_l)
		if l_used == 0 then l_used = 1 end

		cost = l_used * diesel_per_l
	end

	def rental_cost(min)
		time = min_to_time(min)

		#assuming a rental cost of: 160 per 24 hours
		#which will get you a beastly Hyundai 4 door from Hertz 
		if time[:days] > 0 then time[:days] * 160.59
		else 160.59 end
	end

	def min_to_time(minutes)
		seconds = minutes * 60

  		mm, ss = seconds.divmod(60)            
 		hh, mm = mm.divmod(60)           
  		dd, hh = hh.divmod(24) 
  
  		{:hr => hh, :min => mm, :sec => ss.round(0), :days => dd}
	end

	def total_expenses
		@car_cost + @rental_cost + @train_costs + @misc_cost + @air_fare
	end

	def total_time
		@town_stay + @car_time + @train_time
	end


	def self.cheapest_trip(trips)
		trip = trips[0]

		trips.each do |t|
			if t.total_expenses < trip.total_expenses then trip = t end
		end

		trip
	end

	def self.quickest_trip(trips)
		trip = trips[0]

		trips.each do |t|
			if t.total_time < trip.total_time then trip = t end
		end

		trip
	end
end