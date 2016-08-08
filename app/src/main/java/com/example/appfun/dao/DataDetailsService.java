package com.example.appfun.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.appfun.R;
import com.example.appfun.bean.HeroDetailsInfo;
import com.example.appfun.bean.ItemDetailsInfo;
import com.example.appfun.constant.ConstantParms;
import com.example.appfun.url.URLConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zx on 2016/1/25.
 */
public class DataDetailsService {
    public static final String HERODATA_FILENAME = "hero.db";
    public static final String ITEMDATA_FILENAME = "item.db";


    public List<HeroDetailsInfo> getAllHeroData(Context context) {
        SQLiteDatabase db = DatabaseHelper.openDatabase(context, R.raw.hero, HERODATA_FILENAME);
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


    //获取单条数据
    public HeroDetailsInfo findHeroInfo(String heroName, Context context) {
        SQLiteDatabase db = DatabaseHelper.openDatabase(context, R.raw.hero, HERODATA_FILENAME);
        Cursor cursor = db.rawQuery("select * from heroinfo where hero_name=?", new String[]{heroName});
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
        cursor.close();
        db.close();
        return heroDetailsInfo;
    }

    //获取单条数据
    public List<ItemDetailsInfo> getAllItemInfo(Context context) {
        SQLiteDatabase db = DatabaseHelper.openDatabase(context, R.raw.item, ITEMDATA_FILENAME);
        List<ItemDetailsInfo> itemDetailsInfos = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from iteminfo ", null);

        while (cursor.moveToNext()) {
            ItemDetailsInfo itemDetailsInfo = new ItemDetailsInfo();
            itemDetailsInfo.setItem_alias(cursor.getString(cursor.getColumnIndex("item_alias")));
            itemDetailsInfo.setItem_cd(cursor.getInt(cursor.getColumnIndex("item_cd")));
            itemDetailsInfo.setItem_cost(cursor.getString(cursor.getColumnIndex("item_cost")));
            itemDetailsInfo.setItem_effect((cursor.getString(cursor.getColumnIndex("item_effect"))));
            itemDetailsInfo.setItem_info((cursor.getString(cursor.getColumnIndex("item_info"))));
            itemDetailsInfo.setItem_intros((cursor.getString(cursor.getColumnIndex("item_intros"))));
            itemDetailsInfo.setItem_name((cursor.getString(cursor.getColumnIndex("item_name"))));
            itemDetailsInfo.setUrl(URLConfig.ITEM_IMG_URL + cursor.getString(cursor.getColumnIndex("item_alias")) + ConstantParms.largePic);
            itemDetailsInfos.add(itemDetailsInfo);
        }
        cursor.close();
        db.close();
        return itemDetailsInfos;
    }
}
