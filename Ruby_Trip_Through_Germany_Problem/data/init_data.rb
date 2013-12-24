require_relative 'place'
require_relative 'connection'

def add_places
    puts "Initializing Data"

    rostock = Place.create(:name=>"Rostock")
    lubeck = Place.create(:name=>"Lubeck")
    hamburg = Place.create(:name=>"Hamburg")
    bremen = Place.create(:name=>"Bremen")
    hannover = Place.create(:name=>"Hannover", :expenses=>900)
    kassel = Place.create(:name=>"Kassel")
    dusseldorf = Place.create(:name=>"Dusseldorf")
    koln = Place.create(:name=>"Koln", :expenses=>240)
    st_augustine = Place.create(:name=>"St. Augustine")
    bonn = Place.create(:name=>"Bonn")
    wiesbaden = Place.create(:name=>"Wiesbaden")
    frankfurt = Place.create(:name=>"Frankfurt", :entry_city=>true)
    mannheim = Place.create(:name=>"Mannheim")
    karlsruhe = Place.create(:name=>"Karlsruhe")
    baden = Place.create(:name=>"Baden Baden", :stay_time=>1440)
    stuttgart = Place.create(:name=>"Stuttgart", :entry_city=>true)
    munchen = Place.create(:name=>"Munchen", :entry_city=>true)
    nurnberg = Place.create(:name=>"Nurnberg")
    dresden = Place.create(:name=>"Dresden")
    leipzig = Place.create(:name=>"Leipzig")
    berlin = Place.create(:name=>"Berlin", :entry_city=>true)
    basel = Place.create(:name=>"Basel", :expenses=>12000, :country=>"Switzerland")


    #Hannover & berlin north
    Connection.create(:town_a_id=>lubeck.id, :town_b_id=>rostock.id, :car_time=>75, :car_distance=>119, :train_time=>37)
    Connection.create(:town_a_id=>hamburg.id, :town_b_id=>lubeck.id, :car_time=>51, :car_distance=>68, :train_time=>42)
    Connection.create(:town_a_id=>rostock.id, :town_b_id=>berlin.id, :car_time=>135, :car_distance=>230, :train_time=>81)
    Connection.create(:town_a_id=>bremen.id, :town_b_id=>hamburg.id, :car_time=>74, :car_distance=>127, :train_time=>55)
    Connection.create(:town_a_id=>bremen.id, :town_b_id=>hannover.id, :car_time=>78, :car_distance=>128, :train_time=>65)
    Connection.create(:town_a_id=>hannover.id, :town_b_id=>berlin.id, :car_time=>169, :car_distance=>286, :train_time=>97)

    #Hanover & berlin to Kassel & Leipzig
    Connection.create(:town_a_id=>hannover.id, :town_b_id=>dusseldorf.id, :car_time=>161, :car_distance=>278, :train_time=>146)
    Connection.create(:town_a_id=>hannover.id, :town_b_id=>kassel.id, :car_time=>106, :car_distance=>166)
    Connection.create(:town_a_id=>berlin.id, :town_b_id=>leipzig.id, :car_time=>118, :car_distance=>191, :train_time=>75)
    Connection.create(:town_a_id=>berlin.id, :town_b_id=>dresden.id, :car_time=>121, :car_distance=>192, :train_time=>126)
    Connection.create(:town_a_id=>dusseldorf.id, :town_b_id=>kassel.id, :car_time=>140, :car_distance=>228, :train_time=>87)
    Connection.create(:town_a_id=>kassel.id, :town_b_id=>leipzig.id, :car_time=>143, :car_distance=>255)
    Connection.create(:town_a_id=>leipzig.id, :town_b_id=>dresden.id, :car_time=>77, :car_distance=>112, :train_time=>70)

    #kassel & leipzig to Mannheim & Nuremberg.
    Connection.create(:town_a_id=>dusseldorf.id, :town_b_id=>koln.id, :car_time=>38, :car_distance=>39, :train_time=>48)
    Connection.create(:town_a_id=>kassel.id, :town_b_id=>frankfurt.id, :car_time=>115, :car_distance=>193, :train_time=>92)
    Connection.create(:town_a_id=>kassel.id, :town_b_id=>wiesbaden.id, :car_time=>124, :car_distance=>217, :train_time=>88)
    Connection.create(:town_a_id=>kassel.id, :town_b_id=>koln.id, :car_time=>144, :car_distance=>246)
    Connection.create(:town_a_id=>koln.id, :town_b_id=>bonn.id, :car_time=>29, :car_distance=>29, :train_time=>25)
    Connection.create(:town_a_id=>koln.id, :town_b_id=>st_augustine.id, :car_time=>28, :car_distance=>32)
    Connection.create(:town_a_id=>bonn.id, :town_b_id=>st_augustine.id, :car_time=>14, :car_distance=>9)
    Connection.create(:town_a_id=>bonn.id, :town_b_id=>wiesbaden.id, :car_time=>150, :car_distance=>158, :train_time=>97)
    Connection.create(:town_a_id=>bonn.id, :town_b_id=>frankfurt.id, :car_time=>102, :car_distance=>173, :train_time=>82)
    Connection.create(:town_a_id=>wiesbaden.id, :town_b_id=>frankfurt.id, :car_time=>30, :car_distance=>39, :train_time=>33)
    Connection.create(:town_a_id=>st_augustine.id, :town_b_id=>wiesbaden.id, :car_time=>84, :car_distance=>146)
    Connection.create(:town_a_id=>st_augustine.id, :town_b_id=>frankfurt.id, :car_time=>96, :car_distance=>162)
    Connection.create(:town_a_id=>dresden.id, :town_b_id=>nurnberg.id, :car_time=>182, :car_distance=>312, :train_time=>260)
    Connection.create(:town_a_id=>leipzig.id, :town_b_id=>nurnberg.id, :car_time=>165, :car_distance=>283, :train_time=>212)
    Connection.create(:town_a_id=>wiesbaden.id, :town_b_id=>mannheim.id, :car_time=>57, :car_distance=>92, :train_time=>53)
    Connection.create(:town_a_id=>frankfurt.id, :town_b_id=>mannheim.id, :car_time=>57, :car_distance=>84)
    Connection.create(:town_a_id=>mannheim.id, :town_b_id=>nurnberg.id, :car_time=>136, :car_distance=>241)

    #Mannheim & Nuremberg to the end.
    Connection.create(:town_a_id=>mannheim.id, :town_b_id=>karlsruhe.id, :car_time=>46, :car_distance=>71, :train_time=>22)
    Connection.create(:town_a_id=>mannheim.id, :town_b_id=>stuttgart.id, :car_time=>84, :car_distance=>132, :train_time=>38)
    Connection.create(:town_a_id=>nurnberg.id, :town_b_id=>munchen.id, :car_time=>104, :car_distance=>170, :train_time=>70)
    Connection.create(:town_a_id=>nurnberg.id, :town_b_id=>karlsruhe.id, :car_time=>145, :car_distance=>159, :train_time=>194)
    Connection.create(:town_a_id=>karlsruhe.id, :town_b_id=>stuttgart.id, :car_time=>56, :car_distance=>51, :train_time=>53)
    Connection.create(:town_a_id=>karlsruhe.id, :town_b_id=>baden.id, :car_time=>30, :car_distance=>25)
    Connection.create(:town_a_id=>baden.id, :town_b_id=>stuttgart.id, :car_time=>70, :car_distance=>68)
    Connection.create(:town_a_id=>baden.id, :town_b_id=>basel.id, :car_time=>102, :car_distance=>105, :train_time=>88)
    Connection.create(:town_a_id=>basel.id, :town_b_id=>stuttgart.id, :car_time=>153, :car_distance=>164, :train_time=>160)
    Connection.create(:town_a_id=>stuttgart.id, :town_b_id=>munchen.id, :car_time=>141, :car_distance=>145)
    Connection.create(:town_a_id=>basel.id, :town_b_id=>munchen.id, :car_time=>237, :car_distance=>247, :train_time=>285)

    #Shims
    Connection.create(:town_a_id=>st_augustine.id, :town_b_id=>basel.id, :car_time=>255, :car_distance=>463)
    Connection.create(:town_a_id=>rostock.id, :town_b_id=>basel.id, :car_time=>530, :car_distance=>989, :train_time=>644)
    Connection.create(:town_a_id=>bonn.id, :town_b_id=>basel.id, :car_time=>257, :car_distance=>471, :train_time=>258)
    Connection.create(:town_a_id=>dresden.id, :town_b_id=>wiesbaden.id, :car_time=>260, :car_distance=>485, :train_time=>341)
    Connection.create(:town_a_id=>rostock.id, :town_b_id=>wiesbaden.id, :car_time=>370, :car_distance=>691, :train_time=>437)
    Connection.create(:town_a_id=>dusseldorf.id, :town_b_id=>st_augustine.id, :car_time=>53, :car_distance=>71)
    Connection.create(:town_a_id=>karlsruhe.id, :town_b_id=>dusseldorf.id, :car_time=>190, :car_distance=>339, :train_time=>167)
    Connection.create(:town_a_id=>leipzig.id, :town_b_id=>koln.id, :car_time=>270, :car_distance=>498)
    Connection.create(:town_a_id=>frankfurt.id, :town_b_id=>karlsruhe.id, :car_time=>86, :car_distance=>142, :train_time=>80)
    Connection.create(:town_a_id=>basel.id, :town_b_id=>dresden.id, :car_time=>403, :car_distance=>746, :train_time=>481)
    Connection.create(:town_a_id=>frankfurt.id, :town_b_id=>rostock.id, :car_time=>361, :car_distance=>670, :train_time=>346)


end

def initialize_data(db)
    add_places
end


