package com.example.appfun.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.appfun.bean.HeroDetailsInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zx on 2016/1/25.
 */
public class HeroDetailsService {
    public List<HeroDetailsInfo> getAllData(Context context) {
        SQLiteDatabase db = DatabaseHelper.openDatabase(context);
        List<HeroDetailsInfo> heroDetailsInfos = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from heroinfo", null);
        while (cursor.moveToNext()) {
            HeroDetailsInfo heroDetailsInfo = new HeroDetailsInfo();
            heroDetailsInfo.setHero_agi(cursor.getInt(cursor.getColumnIndex("hero_agi")));
            heroDetailsInfo.setHero_pow(cursor.getInt(cursor.getColumnIndex("hero_pow")));
            heroDetailsInfo.setHero_int(cursor.getInt(cursor.getColumnIndex("hero_int")));
            heroDetailsInfo.setHero_spd(cursor.getInt(cursor.getColumnIndex("hero_spd")));
            heroDetailsInfo.setHero_ball(cursor.getInt(cursor.getColumnIndex("hero_ball")));
            heroDetailsInfo.setHero_type(cursor.getInt(cursor.getColumnIndex("hero_type")));
            heroDetailsInfo.setHero_atk_spd(cursor.getInt(cursor.getColumnIndex("hero_atk_spd")));
            heroDetailsInfo.setHero_atk_type(cursor.getInt(cursor.getColumnIndex("hero_atk_type")));
            heroDetailsInfo.setHero_atk_range(cursor.getInt(cursor.getColumnIndex("hero_atk_range")));
            heroDetailsInfo.setHero_view(cursor.getString(cursor.getColumnIndex("hero_view")));
            heroDetailsInfo.setHero_alias(cursor.getString(cursor.getColumnIndex("hero_alias")));
            heroDetailsInfo.setHero_pos(cursor.getString(cursor.getColumnIndex("hero_pos")));
            heroDetailsInfo.setHero_story(cursor.getString(cursor.getColumnIndex("hero_story")));
            heroDetailsInfo.setHero_def_res(cursor.getString(cursor.getColumnIndex("hero_def_res")));
            heroDetailsInfo.setHero_name(cursor.getString(cursor.getColumnIndex("hero_name")));
            heroDetailsInfo.setHero_atk(cursor.getString(cursor.getColumnIndex("hero_atk")));
            heroDetailsInfo.setHero_atk_up(cursor.getFloat(cursor.getColumnIndex("hero_atk_up")));
            heroDetailsInfo.setHero_def(cursor.getFloat(cursor.getColumnIndex("hero_def")));
            heroDetailsInfo.setHero_pow_up(cursor.getFloat(cursor.getColumnIndex("hero_pow_up")));
            heroDetailsInfo.setHero_int_up(cursor.getFloat(cursor.getColumnIndex("hero_int_up")));
            heroDetailsInfo.setHero_agi_up(cursor.getFloat(cursor.getColumnIndex("hero_agi_up")));
            heroDetailsInfos.add(heroDetailsInfo);
        }
        cursor.close();
        db.close();
        return heroDetailsInfos;
    }

    public HeroDetailsInfo findHeroInfo(String heroname, Context context) {
        SQLiteDatabase db = DatabaseHelper.openDatabase(context);
        Cursor cursor = db.rawQuery("select * from heroinfo where hero_name=?", new String[]{heroname});
        HeroDetailsInfo heroDetailsInfo = null;
        while (cursor.moveToNext()) {
            heroDetailsInfo = new HeroDetailsInfo();
            heroDetailsInfo.setHero_agi(cursor.getInt(cursor.getColumnIndex("hero_agi")));
            heroDetailsInfo.setHero_pow(cursor.getInt(cursor.getColumnIndex("hero_pow")));
            heroDetailsInfo.setHero_int(cursor.getInt(cursor.getColumnIndex("hero_int")));
            heroDetailsInfo.setHero_spd(cursor.getInt(cursor.getColumnIndex("hero_spd")));
            heroDetailsInfo.setHero_ball(cursor.getInt(cursor.getColumnIndex("hero_ball")));
            heroDetailsInfo.setHero_type(cursor.getInt(cursor.getColumnIndex("hero_type")));
            heroDetailsInfo.setHero_atk_spd(cursor.getInt(cursor.getColumnIndex("hero_atk_spd")));
            heroDetailsInfo.setHero_atk_type(cursor.getInt(cursor.getColumnIndex("hero_atk_type")));
            heroDetailsInfo.setHero_atk_range(cursor.getInt(cursor.getColumnIndex("hero_atk_range")));
            heroDetailsInfo.setHero_view(cursor.getString(cursor.getColumnIndex("hero_view")));
            heroDetailsInfo.setHero_alias(cursor.getString(cursor.getColumnIndex("hero_alias")));
            heroDetailsInfo.setHero_pos(cursor.getString(cursor.getColumnIndex("hero_pos")));
            heroDetailsInfo.setHero_story(cursor.getString(cursor.getColumnIndex("hero_story")));
            heroDetailsInfo.setHero_def_res(cursor.getString(cursor.getColumnIndex("hero_def_res")));
            heroDetailsInfo.setHero_name(cursor.getString(cursor.getColumnIndex("hero_name")));
            heroDetailsInfo.setHero_atk(cursor.getString(cursor.getColumnIndex("hero_atk")));
            heroDetailsInfo.setHero_atk_up(cursor.getFloat(cursor.getColumnIndex("hero_atk_up")));
            heroDetailsInfo.setHero_def(cursor.getFloat(cursor.getColumnIndex("hero_def")));
            heroDetailsInfo.setHero_pow_up(cursor.getFloat(cursor.getColumnIndex("hero_pow_up")));
            heroDetailsInfo.setHero_int_up(cursor.getFloat(cursor.getColumnIndex("hero_int_up")));
            heroDetailsInfo.setHero_agi_up(cursor.getFloat(cursor.getColumnIndex("hero_agi_up")));
        }
        return heroDetailsInfo;
    }
}
