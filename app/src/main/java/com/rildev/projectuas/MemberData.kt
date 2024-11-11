package com.rildev.projectuas

object MemberData {
    var tim = TeamData.timList
    var member:Array<MemberBank> = arrayOf(
        MemberBank("JohnD",R.drawable.johnny ,"Controller", tim[0]),
        MemberBank("WindahBarusadar", R.drawable.sample,"Duellist", tim[0]),

        MemberBank("Pekkle",R.drawable.loui,"Initiator",tim[1]),
        MemberBank("Tama",R.drawable.johnny ,"Sentinel", tim[1]),
        MemberBank("Charmmy",R.drawable.heri,"Controller",tim[1]),

        MemberBank("SariBeat",R.drawable.taa ,"Sentinel", tim[2]),
        MemberBank("NightHawk",R.drawable.cho,"Duelist",tim[2]),

        MemberBank("Gyodongi", R.drawable.gyodong,"Tank", tim[3]),
        MemberBank("Purin",R.drawable.purin ,"Mage", tim[3]),
        MemberBank("Pocha",R.drawable.pocha ,"Assasin", tim[3]),

        MemberBank("ShadowStrike",R.drawable.bren,"Fighter",tim[4]),
        MemberBank("BlazeWolf",R.drawable.il ,"Marksman", tim[4]),

        MemberBank("PhantomFury",R.drawable.junk,"Support",tim[5]),
        MemberBank("NovaKnight",R.drawable.sample ,"Tank", tim[5]),

        MemberBank("MysticGamer",R.drawable.loui,"Top Lane",tim[6]),
        MemberBank("Nobody",R.drawable.bren ,"Mid Lane", tim[6]),
        MemberBank("Faker",R.drawable.cho,"Jungle",tim[6]),

        MemberBank("Nobody",R.drawable.heri ,"Top Lane", tim[7]),
        MemberBank("Faker",R.drawable.cho,"Jungle",tim[7]),

        //SEMENTARA SEMUA DIKASI MEMBER, KLO GK ADA MEMBER JG TTP BS DIRUN HEHEHE
        MemberBank("Zeus",R.drawable.sample ,"Support", tim[8]),
        MemberBank("Heryl", R.drawable.taa,"Mid Lane", tim[8])
    )
}