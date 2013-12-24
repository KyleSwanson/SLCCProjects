require_relative 'init_data'

#L.A.M.E
#LAME And Me Equip Databases

require 'dbi'

class Database
        attr_accessor :connection

        @@name = "trip_db"
        @@user = "kyle"
        @@place_table = "place_table"
        @@conn_table = "conn_table"
        @@connect = "DBI:SQLite3: trip_db"

        def self.info
            {:name=>@@name, :user=>@@user, :connect=>@@connect, :place_table=>@@place_table, :conn_table=>@@conn_table}
        end

        @@place_name_col = "name"
        @@place_stay_col = "stay"
        @@place_entry_col = "entry"
        @@place_expense_col = "expense"
        @@place_country_col = "country"

        @@conn_town_a_id = "town_a_id"
        @@conn_town_b_id = "town_b_id"
        @@conn_train_cost = "train_cost"
        @@conn_car_dist = "car_distance"
        @@conn_train_time = "train_time"
        @@conn_car_time = "car_time"

        @connection
        
        def self.connection
            @connection
        end

        def initialize(*args)
            @connection = connect 

            #This should be 1 complete block
            drop
            create_connections
            create_places
            initialize_data(self)
            #to here
        end

        def connect
            return DBI.connect(@@connect, @@user)
        end
        
        def create_places
            puts "Creating Table #{@@place_table}"

            begin
                @connection.do("CREATE TABLE #{@@place_table}(
                    id INTEGER PRIMARY KEY,
                    #{@@place_name_col} CHAR(64),
                    #{@@place_stay_col} INTEGER,
                    #{@@place_entry_col} CHAR(64),
                    #{@@place_expense_col} REAL,
                    #{@@place_country_col} CHAR(64) );") 
            rescue Exception => e
                init=false 
                puts @connection
                puts "Debug (lamedb.rb): #{e.message}"
                #puts e.backtrace.inspect
            end
        end

        def create_connections
            puts "Creating Table #{@@conn_table}"

            begin
                @connection.do("CREATE TABLE #{@@conn_table}(
                    id INTEGER PRIMARY KEY,
                    #{@@conn_town_a_id} INTEGER,
                    #{@@conn_town_b_id} INTEGER,
                    #{@@conn_train_cost} REAL,
                    #{@@conn_car_dist} INTEGER,
                    #{@@conn_train_time} INTEGER,
                    #{@@conn_car_time} INTEGER
                    );") 

            rescue Exception => e
                init=false 
                puts "Debug (lamedb.rb): #{e.message}"
                #puts e.backtrace.inspect
            end

        end

        def drop
            puts "Dropping Table #{@@place_table}"
            @connection.do("DROP TABLE IF EXISTS #{@@place_table}")

            puts "Dropping Table #{@@conn_table}"
            @connection.do("DROP TABLE IF EXISTS #{@@conn_table}")
        end
end