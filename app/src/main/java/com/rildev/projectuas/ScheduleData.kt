package com.rildev.projectuas

object ScheduleData {
    // var tanggalEvent:String, var waktuEvent:String, var namaEvent:String, var TempatEvent:String, var cabangLomba: CabangBank,
    // var tim: TeamBank, var deskripsi:String
    // cabang >> valo, ml, lol >> URUT

    var cabang = CabangData.cabangs
    var tim = TeamData.timList

    var schedule = arrayOf(
        ScheduleBank("17-10-2024", "10.00 AM", "Regional Qualifier - Valorant", "Los Angeles, CA",
            cabang[0], tim[0], "This high-stakes event will bring together top teams from across the region, " +
                    "all competing for a chance to advance to the national finals. Expect intense gameplay, strategic plays, and " +
                    "thrilling moments as teams battle it out in one of the most popular first-person shooters. Fans can anticipate " +
                    "an action-packed day filled with memorable highlights and fierce competition in the heart of the esports scene.", R.drawable.regional_qualifier_valorant),
        ScheduleBank("16-10-2024", "11.00 AM", "National Championship in Mobile Legends, Only For Juniors", "Surabaya, Id",
            cabang[1], tim[4], "This highly anticipated event will bring together the top Mobile Legends teams from across the nation, " +
                    "all competing for the ultimate title of national champion. Prepare for epic battles, strategic plays, and intense moments " +
                    "as the best players showcase their skills in the fast-paced world of Mobile Legends. Fans can expect an action-packed " +
                    "tournament filled with unforgettable highlights, thrilling comebacks, and fierce competition as teams fight for glory in " +
                    "one of the most beloved mobile esports titles. Get ready to witness the best Mobile Legends action at the heart of the " +
                    "championship!", R.drawable.ml_championship),
        ScheduleBank("15-10-2024", "11.00 AM", "National Championship in Mobile Legends, Only For Juniors", "Surabaya, Id",
            cabang[1], tim[4], "This highly anticipated event will bring together the top Mobile Legends teams from across the nation, " +
                    "all competing for the ultimate title of national champion. Prepare for epic battles, strategic plays, and intense moments " +
                    "as the best players showcase their skills in the fast-paced world of Mobile Legends. Fans can expect an action-packed " +
                    "tournament filled with unforgettable highlights, thrilling comebacks, and fierce competition as teams fight for glory in " +
                    "one of the most beloved mobile esports titles. Get ready to witness the best Mobile Legends action at the heart of the " +
                    "championship!", R.drawable.ml_championship)
    )
}