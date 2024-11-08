package com.rildev.projectuas


object AchievementData
{
    var cabang = CabangData.cabangs
    var tim = TeamData.timList
    //notes : cabang >> valo, ml, lol >> URUT
    var achievement = arrayOf(
        Achievement(cabang[0],2023,
            "Champions of the Regional Valorant Shutdown", tim[0]),
        Achievement(cabang[0],2024,
            "Best Defensive Team Award", tim[1]),
        Achievement(cabang[0],2023,
            "MVP of the Season", tim[0]),
        Achievement(cabang[0],2022,
            "Flawless Victory at the Spring Valorant Cup",tim[0]),
        Achievement(cabang[0],2023,
            "Top 4 Finish in the International Valorant Championship",tim[2]),

        Achievement(cabang[1],2022,
            "M4 World Championship", tim[4]),
        Achievement(cabang[1],2022,
            "MPL Philippines Season 9", tim[4]),
        Achievement(cabang[1],2023,
            "M5 World Championship",tim[3]),
        Achievement(cabang[1],2024,
            "MPL Season 12", tim[5]),
        Achievement(cabang[1],2024,
            "MPL Malaysia/Singapore Season 10",tim[5]),

        Achievement(cabang[2],2022,
            "LCK Spring Split 2022",tim[6]),
        Achievement(cabang[2],2024,
            "Worlds 2024",tim[7]),
        Achievement(cabang[2],2023,
            "LEC Summer Split 2023",tim[8])
    )
}

