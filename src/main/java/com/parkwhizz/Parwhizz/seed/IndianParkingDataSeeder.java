package com.parkwhizz.Parwhizz.seed;

import com.parkwhizz.Parwhizz.model.Parking;
import com.parkwhizz.Parwhizz.model.Spot;
import com.parkwhizz.Parwhizz.repository.ParkingRepository;
import com.parkwhizz.Parwhizz.repository.SpotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@Order(1)
public class IndianParkingDataSeeder implements CommandLineRunner {

    @Autowired
    private ParkingRepository parkingRepository;

    @Autowired
    private SpotRepository spotRepository;

    @Override
    public void run(String... args) throws Exception {
        // Only seed if database is empty
        if (parkingRepository.count() > 0) {
            System.out.println("Parking data already exists. Skipping seed.");
            return;
        }

        System.out.println("Seeding Indian parking data...");
        seedMumbaiParkings();
        seedDelhiParkings();
        seedBangaloreParkings();
        seedChennaiParkings();
        seedKolkataParkings();
        seedHyderabadParkings();
        seedPuneParkings();
        seedAhmedabadParkings();
        seedJaipurParkings();
        System.out.println("Indian parking data seeded successfully!");
    }

    private void seedMumbaiParkings() {
        List<Parking> parkings = new ArrayList<>();
        
        parkings.add(createParking("Bandra West Premium Parking", "Mumbai", "Maharashtra",
                "Plot 21, Linking Road, Bandra West, Mumbai - 400050", 150.0, 20));
        parkings.add(createParking("Andheri Metro Station Parking", "Mumbai", "Maharashtra",
                "Western Express Highway, Andheri East, Mumbai - 400069", 100.0, 50));
        parkings.add(createParking("Colaba Causeway Parking", "Mumbai", "Maharashtra",
                "Shahid Bhagat Singh Road, Colaba, Mumbai - 400001", 200.0, 15));
        parkings.add(createParking("BKC Business District Parking", "Mumbai", "Maharashtra",
                "G Block, Bandra Kurla Complex, Mumbai - 400051", 180.0, 100));
        parkings.add(createParking("Juhu Beach Parking", "Mumbai", "Maharashtra",
                "Juhu Tara Road, Juhu, Mumbai - 400049", 120.0, 40));
        parkings.add(createParking("Powai Lake View Parking", "Mumbai", "Maharashtra",
                "Powai Vihar Complex, Powai, Mumbai - 400076", 130.0, 30));
        parkings.add(createParking("Lower Parel Mill Parking", "Mumbai", "Maharashtra",
                "Senapati Bapat Marg, Lower Parel, Mumbai - 400013", 160.0, 45));
        parkings.add(createParking("Dadar Station Parking", "Mumbai", "Maharashtra",
                "Dr. Babasaheb Ambedkar Road, Dadar West, Mumbai - 400028", 90.0, 60));
        parkings.add(createParking("Churchgate Station Parking", "Mumbai", "Maharashtra",
                "Veer Nariman Road, Churchgate, Mumbai - 400020", 140.0, 25));
        parkings.add(createParking("Malad Link Road Parking", "Mumbai", "Maharashtra",
                "Link Road, Malad West, Mumbai - 400064", 110.0, 35));
        parkings.add(createParking("Worli Sea Face Parking", "Mumbai", "Maharashtra",
                "Worli Sea Face Road, Worli, Mumbai - 400018", 170.0, 20));
        parkings.add(createParking("Borivali National Park Parking", "Mumbai", "Maharashtra",
                "Sanjay Gandhi National Park, Borivali East, Mumbai - 400066", 80.0, 80));
        parkings.add(createParking("Phoenix Mall Parking", "Mumbai", "Maharashtra",
                "High Street Phoenix, Lower Parel, Mumbai - 400013", 150.0, 200));
        parkings.add(createParking("Kurla Terminus Parking", "Mumbai", "Maharashtra",
                "LBS Marg, Kurla West, Mumbai - 400070", 95.0, 70));
        parkings.add(createParking("Ghatkopar Metro Parking", "Mumbai", "Maharashtra",
                "Ghatkopar-Mankhurd Link Road, Ghatkopar East, Mumbai - 400077", 105.0, 55));

        parkingRepository.saveAll(parkings);
    }

    private void seedDelhiParkings() {
        List<Parking> parkings = new ArrayList<>();

        parkings.add(createParking("Connaught Place Central Parking", "New Delhi", "Delhi",
                "Connaught Place, New Delhi - 110001", 180.0, 150));
        parkings.add(createParking("Karol Bagh Market Parking", "New Delhi", "Delhi",
                "Ajmal Khan Road, Karol Bagh, New Delhi - 110005", 120.0, 80));
        parkings.add(createParking("Saket Metro Station Parking", "New Delhi", "Delhi",
                "Press Enclave Road, Saket, New Delhi - 110017", 110.0, 100));
        parkings.add(createParking("Chandni Chowk Parking", "New Delhi", "Delhi",
                "Chandni Chowk Road, Old Delhi - 110006", 100.0, 40));
        parkings.add(createParking("Nehru Place IT Hub Parking", "New Delhi", "Delhi",
                "Nehru Place Commercial Complex, New Delhi - 110019", 140.0, 120));
        parkings.add(createParking("Rajiv Chowk Metro Parking", "New Delhi", "Delhi",
                "Barakhamba Road, Rajiv Chowk, New Delhi - 110001", 160.0, 90));
        parkings.add(createParking("Hauz Khas Village Parking", "New Delhi", "Delhi",
                "Hauz Khas Village, New Delhi - 110016", 130.0, 60));
        parkings.add(createParking("Dwarka Sector 21 Metro Parking", "New Delhi", "Delhi",
                "Sector 21, Dwarka, New Delhi - 110075", 90.0, 200));
        parkings.add(createParking("Pitampura Mall Parking", "New Delhi", "Delhi",
                "Rohini-Pitampura Road, Pitampura, New Delhi - 110034", 115.0, 150));
        parkings.add(createParking("Lajpat Nagar Market Parking", "New Delhi", "Delhi",
                "Lajpat Nagar Central Market, New Delhi - 110024", 125.0, 70));
        parkings.add(createParking("Vasant Kunj Mall Parking", "New Delhi", "Delhi",
                "Vasant Kunj, New Delhi - 110070", 135.0, 180));
        parkings.add(createParking("Greater Kailash M Block Parking", "New Delhi", "Delhi",
                "M Block Market, Greater Kailash 1, New Delhi - 110048", 145.0, 50));
        parkings.add(createParking("Rohini Sector 3 Parking", "New Delhi", "Delhi",
                "Sector 3, Rohini, New Delhi - 110085", 100.0, 100));
        parkings.add(createParking("Janakpuri District Center Parking", "New Delhi", "Delhi",
                "District Centre, Janakpuri, New Delhi - 110058", 105.0, 120));
        parkings.add(createParking("Rajouri Garden Metro Parking", "New Delhi", "Delhi",
                "Rajouri Garden, New Delhi - 110027", 110.0, 85));

        parkingRepository.saveAll(parkings);
    }

    private void seedBangaloreParkings() {
        List<Parking> parkings = new ArrayList<>();

        parkings.add(createParking("MG Road Premium Parking", "Bangalore", "Karnataka",
                "Mahatma Gandhi Road, Bangalore - 560001", 150.0, 80));
        parkings.add(createParking("Koramangala 4th Block Parking", "Bangalore", "Karnataka",
                "80 Feet Road, Koramangala 4th Block, Bangalore - 560034", 140.0, 100));
        parkings.add(createParking("Indiranagar 100 Feet Road Parking", "Bangalore", "Karnataka",
                "100 Feet Road, Indiranagar, Bangalore - 560038", 130.0, 70));
        parkings.add(createParking("Electronic City Tech Park Parking", "Bangalore", "Karnataka",
                "Electronic City Phase 1, Bangalore - 560100", 110.0, 300));
        parkings.add(createParking("Whitefield ITPL Parking", "Bangalore", "Karnataka",
                "ITPL Main Road, Whitefield, Bangalore - 560066", 120.0, 250));
        parkings.add(createParking("Commercial Street Parking", "Bangalore", "Karnataka",
                "Commercial Street, Bangalore - 560001", 160.0, 50));
        parkings.add(createParking("HSR Layout Sector 1 Parking", "Bangalore", "Karnataka",
                "27th Main Road, HSR Layout, Bangalore - 560102", 125.0, 90));
        parkings.add(createParking("Jayanagar 4th Block Parking", "Bangalore", "Karnataka",
                "4th Block, Jayanagar, Bangalore - 560011", 115.0, 60));
        parkings.add(createParking("Malleshwaram Circle Parking", "Bangalore", "Karnataka",
                "Sampige Road, Malleshwaram, Bangalore - 560003", 120.0, 55));
        parkings.add(createParking("Bannerghatta Road Parking", "Bangalore", "Karnataka",
                "Bannerghatta Main Road, Bangalore - 560076", 100.0, 120));
        parkings.add(createParking("Bellandur ORR Parking", "Bangalore", "Karnataka",
                "Outer Ring Road, Bellandur, Bangalore - 560103", 130.0, 150));
        parkings.add(createParking("Yeshwanthpur Metro Parking", "Bangalore", "Karnataka",
                "Yeshwanthpur, Bangalore - 560022", 105.0, 180));
        parkings.add(createParking("Brigade Road Parking", "Bangalore", "Karnataka",
                "Brigade Road, Bangalore - 560001", 155.0, 45));
        parkings.add(createParking("Marathahalli Bridge Parking", "Bangalore", "Karnataka",
                "Marathahalli, Bangalore - 560037", 115.0, 110));
        parkings.add(createParking("RT Nagar Parking", "Bangalore", "Karnataka",
                "RT Nagar Main Road, Bangalore - 560032", 95.0, 75));

        parkingRepository.saveAll(parkings);
    }

    private void seedChennaiParkings() {
        List<Parking> parkings = new ArrayList<>();

        parkings.add(createParking("T Nagar Ranganathan Street Parking", "Chennai", "Tamil Nadu",
                "Ranganathan Street, T Nagar, Chennai - 600017", 130.0, 80));
        parkings.add(createParking("Anna Nagar Tower Park Parking", "Chennai", "Tamil Nadu",
                "Second Avenue, Anna Nagar, Chennai - 600040", 115.0, 100));
        parkings.add(createParking("Adyar Besant Nagar Beach Parking", "Chennai", "Tamil Nadu",
                "Besant Nagar Beach Road, Chennai - 600090", 120.0, 150));
        parkings.add(createParking("Guindy IT Park Parking", "Chennai", "Tamil Nadu",
                "Rajiv Gandhi Salai, Guindy, Chennai - 600032", 110.0, 200));
        parkings.add(createParking("Mylapore Kapaleeshwarar Temple Parking", "Chennai", "Tamil Nadu",
                "North Mada Street, Mylapore, Chennai - 600004", 100.0, 50));
        parkings.add(createParking("Velachery Main Road Parking", "Chennai", "Tamil Nadu",
                "Velachery Main Road, Chennai - 600042", 105.0, 120));
        parkings.add(createParking("Nungambakkam High Road Parking", "Chennai", "Tamil Nadu",
                "Nungambakkam High Road, Chennai - 600034", 125.0, 70));
        parkings.add(createParking("OMR Thoraipakkam Parking", "Chennai", "Tamil Nadu",
                "Old Mahabalipuram Road, Thoraipakkam, Chennai - 600096", 115.0, 180));
        parkings.add(createParking("Porur Junction Parking", "Chennai", "Tamil Nadu",
                "Mount Poonamallee Road, Porur, Chennai - 600116", 95.0, 140));
        parkings.add(createParking("Washermanpet Metro Parking", "Chennai", "Tamil Nadu",
                "Washermanpet, Chennai - 600021", 90.0, 90));

        parkingRepository.saveAll(parkings);
    }

    private void seedKolkataParkings() {
        List<Parking> parkings = new ArrayList<>();

        parkings.add(createParking("Park Street Premium Parking", "Kolkata", "West Bengal",
                "Park Street, Kolkata - 700016", 140.0, 100));
        parkings.add(createParking("Salt Lake Sector 5 IT Hub Parking", "Kolkata", "West Bengal",
                "Sector 5, Salt Lake, Kolkata - 700091", 120.0, 250));
        parkings.add(createParking("New Market Shopping Parking", "Kolkata", "West Bengal",
                "Lindsay Street, New Market, Kolkata - 700087", 110.0, 60));
        parkings.add(createParking("Howrah Station Parking", "Kolkata", "West Bengal",
                "Howrah Station Road, Howrah - 711101", 95.0, 200));
        parkings.add(createParking("Esplanade Metro Parking", "Kolkata", "West Bengal",
                "Jawaharlal Nehru Road, Esplanade, Kolkata - 700001", 130.0, 80));
        parkings.add(createParking("Rajarhat New Town Parking", "Kolkata", "West Bengal",
                "Action Area 1, Rajarhat, Kolkata - 700156", 100.0, 180));
        parkings.add(createParking("Gariahat Market Parking", "Kolkata", "West Bengal",
                "Gariahat Road, Kolkata - 700019", 115.0, 70));
        parkings.add(createParking("Ballygunge Circular Road Parking", "Kolkata", "West Bengal",
                "Ballygunge Circular Road, Kolkata - 700019", 125.0, 60));
        parkings.add(createParking("EM Bypass Parking", "Kolkata", "West Bengal",
                "Eastern Metropolitan Bypass, Kolkata - 700107", 105.0, 150));
        parkings.add(createParking("Behala Chowrasta Parking", "Kolkata", "West Bengal",
                "Behala Chowrasta, Kolkata - 700034", 90.0, 100));

        parkingRepository.saveAll(parkings);
    }

    private void seedHyderabadParkings() {
        List<Parking> parkings = new ArrayList<>();

        parkings.add(createParking("Banjara Hills Road 1 Parking", "Hyderabad", "Telangana",
                "Road Number 1, Banjara Hills, Hyderabad - 500034", 135.0, 90));
        parkings.add(createParking("HITEC City Cyber Towers Parking", "Hyderabad", "Telangana",
                "HITEC City, Madhapur, Hyderabad - 500081", 125.0, 300));
        parkings.add(createParking("Begumpet Airport Metro Parking", "Hyderabad", "Telangana",
                "Begumpet, Hyderabad - 500016", 110.0, 200));
        parkings.add(createParking("Ameerpet Metro Station Parking", "Hyderabad", "Telangana",
                "Ameerpet, Hyderabad - 500038", 105.0, 150));
        parkings.add(createParking("Gachibowli DLF Parking", "Hyderabad", "Telangana",
                "Gachibowli, Hyderabad - 500032", 120.0, 250));
        parkings.add(createParking("Kukatpally Housing Board Colony Parking", "Hyderabad", "Telangana",
                "KPHB Colony, Kukatpally, Hyderabad - 500072", 95.0, 120));
        parkings.add(createParking("Secunderabad Station Parking", "Hyderabad", "Telangana",
                "Secunderabad Railway Station, Hyderabad - 500003", 100.0, 180));
        parkings.add(createParking("Jubilee Hills CheckPost Parking", "Hyderabad", "Telangana",
                "Road Number 36, Jubilee Hills, Hyderabad - 500033", 130.0, 80));
        parkings.add(createParking("Miyapur Metro Parking", "Hyderabad", "Telangana",
                "Miyapur, Hyderabad - 500049", 90.0, 200));
        parkings.add(createParking("Charminar Heritage Parking", "Hyderabad", "Telangana",
                "Charminar Road, Hyderabad - 500002", 115.0, 60));

        parkingRepository.saveAll(parkings);
    }

    private void seedPuneParkings() {
        List<Parking> parkings = new ArrayList<>();

        parkings.add(createParking("Koregaon Park Premium Parking", "Pune", "Maharashtra",
                "Koregaon Park, Pune - 411001", 130.0, 80));
        parkings.add(createParking("Hinjewadi IT Park Parking", "Pune", "Maharashtra",
                "Rajiv Gandhi Infotech Park, Hinjewadi, Pune - 411057", 115.0, 400));
        parkings.add(createParking("FC Road Shopping Parking", "Pune", "Maharashtra",
                "Fergusson College Road, Pune - 411004", 125.0, 70));
        parkings.add(createParking("Baner Pashan Link Road Parking", "Pune", "Maharashtra",
                "Baner-Pashan Link Road, Pune - 411021", 110.0, 150));
        parkings.add(createParking("Kothrud Karve Road Parking", "Pune", "Maharashtra",
                "Karve Road, Kothrud, Pune - 411038", 105.0, 90));
        parkings.add(createParking("Viman Nagar Airport Road Parking", "Pune", "Maharashtra",
                "Airport Road, Viman Nagar, Pune - 411014", 120.0, 120));
        parkings.add(createParking("Aundh IT Park Parking", "Pune", "Maharashtra",
                "Aundh, Pune - 411007", 115.0, 200));
        parkings.add(createParking("Shivajinagar Station Parking", "Pune", "Maharashtra",
                "Shivajinagar, Pune - 411005", 100.0, 110));
        parkings.add(createParking("Wakad Hinjewadi Road Parking", "Pune", "Maharashtra",
                "Wakad-Hinjewadi Road, Wakad, Pune - 411057", 105.0, 180));
        parkings.add(createParking("Magarpatta City Parking", "Pune", "Maharashtra",
                "Magarpatta City, Hadapsar, Pune - 411028", 120.0, 250));

        parkingRepository.saveAll(parkings);
    }

    private void seedAhmedabadParkings() {
        List<Parking> parkings = new ArrayList<>();

        parkings.add(createParking("SG Highway Premium Parking", "Ahmedabad", "Gujarat",
                "Sarkhej-Gandhinagar Highway, Ahmedabad - 380015", 125.0, 150));
        parkings.add(createParking("CG Road Shopping Parking", "Ahmedabad", "Gujarat",
                "CG Road, Navrangpura, Ahmedabad - 380009", 120.0, 80));
        parkings.add(createParking("Maninagar Station Parking", "Ahmedabad", "Gujarat",
                "Maninagar, Ahmedabad - 380008", 95.0, 120));
        parkings.add(createParking("Vastrapur Lake Parking", "Ahmedabad", "Gujarat",
                "Vastrapur Lake Road, Ahmedabad - 380015", 105.0, 100));
        parkings.add(createParking("Prahladnagar Corporate Park Parking", "Ahmedabad", "Gujarat",
                "Prahladnagar, Ahmedabad - 380015", 115.0, 200));
        parkings.add(createParking("Law Garden Market Parking", "Ahmedabad", "Gujarat",
                "Law Garden Road, Ahmedabad - 380006", 110.0, 60));
        parkings.add(createParking("Satellite Jodhpur Cross Roads Parking", "Ahmedabad", "Gujarat",
                "Satellite, Ahmedabad - 380015", 100.0, 140));
        parkings.add(createParking("Bopal Ambli Road Parking", "Ahmedabad", "Gujarat",
                "Bopal-Ambli Road, Ahmedabad - 380058", 95.0, 160));

        parkingRepository.saveAll(parkings);
    }

    private void seedJaipurParkings() {
        List<Parking> parkings = new ArrayList<>();

        parkings.add(createParking("MI Road Shopping Parking", "Jaipur", "Rajasthan",
                "Mirza Ismail Road, Jaipur - 302001", 120.0, 90));
        parkings.add(createParking("Malviya Nagar Metro Parking", "Jaipur", "Rajasthan",
                "Malviya Nagar, Jaipur - 302017", 105.0, 150));
        parkings.add(createParking("Vaishali Nagar Parking", "Jaipur", "Rajasthan",
                "Vaishali Nagar, Jaipur - 302021", 100.0, 120));
        parkings.add(createParking("Tonk Road IT Park Parking", "Jaipur", "Rajasthan",
                "Tonk Road, Jaipur - 302015", 110.0, 200));
        parkings.add(createParking("C-Scheme Shopping Parking", "Jaipur", "Rajasthan",
                "C-Scheme, Jaipur - 302001", 115.0, 70));
        parkings.add(createParking("Jagatpura Flyover Parking", "Jaipur", "Rajasthan",
                "Jagatpura Road, Jaipur - 302017", 95.0, 140));
        parkings.add(createParking("Mansarovar Metro Parking", "Jaipur", "Rajasthan",
                "Mansarovar, Jaipur - 302020", 100.0, 160));

        parkingRepository.saveAll(parkings);
    }

    private Parking createParking(String name, String city, String state, String address, 
                                  Double price, int totalSpots) {
        Parking parking = new Parking();
        parking.setName(name);
        parking.setCity(city);
        parking.setState(state);
        parking.setAddress_1(address);
        parking.setAddress_2(state);  // Using address_2 for state as well
        parking.setPrice(price.floatValue());  // Convert Double to Float
        parking.setTotalSpots(totalSpots);
        parking.setAvailableSpots(totalSpots);
        
        // Save parking first to get ID
        Parking savedParking = parkingRepository.save(parking);
        
        // Create spots for this parking
        createSpotsForParking(savedParking, totalSpots);
        
        return savedParking;
    }

    private void createSpotsForParking(Parking parking, int totalSpots) {
        // Create spots in batches of 50 to avoid MongoDB connection issues
        int batchSize = 50;
        for (int batchStart = 1; batchStart <= totalSpots; batchStart += batchSize) {
            List<Spot> spots = new ArrayList<>();
            int batchEnd = Math.min(batchStart + batchSize - 1, totalSpots);
            
            for (int i = batchStart; i <= batchEnd; i++) {
                Spot spot = new Spot();
                spot.setSpotNo(String.format("P%03d", i));
                spot.setSpotStatus("UnBooked");
                // Add parking to the Set
                Set<Parking> parkingSet = new HashSet<>();
                parkingSet.add(parking);
                spot.setParkings(parkingSet);
                spots.add(spot);
            }
            
            // Save this batch
            if (!spots.isEmpty()) {
                spotRepository.saveAll(spots);
            }
        }
    }
}
