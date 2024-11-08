package com.rildev.projectuas

object TeamData {
    var cabang = CabangData.cabangs
    var timList = arrayOf(
        //VALORANT
        TeamBank("Valo A", cabang[0]),
        TeamBank("Valo B", cabang[0]),
        TeamBank("Valo C", cabang[0]),

        //ML
        TeamBank("ML A", cabang[1]),
        TeamBank("ML B", cabang[1]),
        TeamBank("ML C", cabang[1]),

        //LOL
        TeamBank("Kocak Geming", cabang[2]),
        TeamBank("Bocil Kematian", cabang[2]),
        TeamBank("Bocil Kehidupan", cabang[2])
    )
}