# encoding: utf-8

class Place
    attr_accessor :id, :name, :stay_time, :entry_city, :expenses, :country

    def initialize(extras={})
        @id = extras[:id]
        @name = extras[:name]
        @stay_time = extras[:stay_time] || 480 
        @entry_city = extras[:entry_city] || false
        @expenses = extras[:expenses] || 0
        @country = extras[:country] || "Germany"
    end

    def inspect
        out = "#{@id} - Town: #{@name}"
        if @stay_time != 8 then out+= ", Stay: #{@stay_time}" end
        if @entry_city then out += ", Entry City: #{@entry_city}" end
        if @expenses > 0 then out += ", Extra Expenses: €#{@expenses}" end
        if @country != "Germany" then out += ", Country: #{@country}" end

        out
    end

    def entry?
        @entry_city
    end

    def self.create(extras={}) 
        to_insert = Place.new(extras)
            @connection

        begin
            dbh = DBI.connect(Database.info[:connect], Database.info[:user])

            dbh.do("insert into #{Database.info[:place_table]} values 
                (NULL,
                '#{to_insert.name}', 
                '#{to_insert.stay_time}',
                '#{to_insert.entry_city}',
                '#{to_insert.expenses}',
                '#{to_insert.country}'
                )")

            #puts dbh.methods
            to_insert.id = dbh.select_one("SELECT last_insert_rowid()")[0].to_i
            dbh.disconnect

            return to_insert
        rescue Exception => e
            puts "Debug: #{e.message}"
        end
    end

    def self.all
        self.find_where(nil)
    end

    def self.all_ids
        all = self.find_where(nil)

        ids = []

        all.each do |i|
            ids << i.id
        end

        ids
    end

    def self.find_by_id(id)
        find_where("id = '#{id}'")[0]
    end

    def self.entry_cities
        find_where("entry = 'true'")
    end

    def self.find_where(where_arg)
        begin
            dbh = DBI.connect(Database.info[:connect], Database.info[:user])

            if where_arg.nil?
                rows = dbh.select_all("SELECT * FROM #{Database.info[:place_table]}")
            else
                rows = dbh.select_all("SELECT * FROM #{Database.info[:place_table]} WHERE #{where_arg}")
            end

            places = []

            rows.each do |i|
                places << place_from_rows(i)
            end

            dbh.disconnect

            places
        rescue Exception => e
            puts "Debug: #{e.message}"
        end
    end

    def self.place_from_rows(row)
        Place.new(
            :id=>row["id"],
            :name=>row["name"], 
            :stay_time=>row["stay"], 
            :entry_city=>row["entry"], 
            :expenses=>row["expense"], 
            :country=>row["country"])
    end
end

