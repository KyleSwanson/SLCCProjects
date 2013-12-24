require_relative 'lamedb'

class Stage
  attr_accessor :town, :time, :form

  def initialize(town, time, form)
    @town = town
    @time = time
    @form = form
  end
end

class Connection
    attr_accessor :id, :town_a_id, :town_b_id, :train_cost, :train_time, :car_distance, :car_time

    #distances in km, times in minutes

    def initialize(extras={})
        @town_a_id = extras[:town_a_id]
        @town_b_id = extras[:town_b_id]
        @train_cost = extras[:train_cost] || -1
        @train_time = extras[:train_time] || -1
        @car_distance = extras[:car_distance] || -1
        @car_time = extras[:car_time] || -1
    end

    def town_a
        Place.find_by_id(@town_a_id)
    end

    def town_b
        Place.find_by_id(@town_b_id)
    end

    def other_town(id)
      if id == @town_a_id then Place.find_by_id(@town_b_id) 
      else Place.find_by_id(@town_a_id) end

    end

    def inspect
        "#{town_a.name} to #{town_b.name}"
    end

    def self.create(extras={})
      to_insert = Connection.new(extras)
          @connection

      begin
          dbh = DBI.connect(Database.info[:connect], Database.info[:user])

          dbh.do("insert into #{Database.info[:conn_table]} values 
              (NULL,
              '#{to_insert.town_a_id}', 
              '#{to_insert.town_b_id}',
              '#{to_insert.train_cost}',
              '#{to_insert.car_distance}',
              '#{to_insert.train_time}',
              '#{to_insert.car_time}'
              )")

          #puts dbh.methods
          to_insert.id = dbh.select_one("SELECT last_insert_rowid()")[0].to_i
          dbh.disconnect

          return to_insert
      rescue Exception => e
        puts e.backtrace
        puts "Debug (Connection.rb): #{e.message}"
      end
    end

    def self.all
      find_where(nil)
    end

    def self.find_by_town_id(id)
      find_where("town_a_id = '#{id}' or town_b_id = '#{id}'")
    end

    def self.find_where(where_arg)
        begin
            dbh = DBI.connect(Database.info[:connect], Database.info[:user])

            if where_arg.nil?
                rows = dbh.select_all("SELECT * FROM #{Database.info[:conn_table]}")
            else
                rows = dbh.select_all("SELECT * FROM #{Database.info[:conn_table]} WHERE #{where_arg}")
            end

            conns = []

            rows.each do |i|
                conns << connection_from_rows(i)
            end

            dbh.disconnect

            conns
        rescue Exception => e
            puts "Debug: #{e.message}"
        end
    end

    def self.connection_from_rows(rows)
      Connection.new(
        :id=>rows["id"], 
        :town_a_id=>rows["town_a_id"],
        :town_b_id=>rows["town_b_id"],
        :train_cost=>rows["train_cost"],
        :train_time=>rows["train_time"],
        :car_distance=>rows["car_distance"],
        :car_time=>rows["car_time"])
    end
end