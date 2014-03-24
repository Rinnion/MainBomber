package com.me.assetloader;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Model;


/**
 * Created with IntelliJ IDEA.
 * User: alekseev
 * Date: 14.02.14
 * Time: 16:31
 * To change this template use File | Settings | File Templates.
 */
public class AssetLoader {

    private static AssetManager mAssetManager=new AssetManager();


    public static Pixmap GetPixmap(String name)
    {
        return (Pixmap)mAssetManager.get(name);
    }

    public static Texture GetTexture(String name)
    {
        return (Texture)mAssetManager.get(name);
    }


    public static Model GetModel(String name)
    {
       return (Model)mAssetManager.get(name);
    }


    public static <T> void load(String fileName,Class<T> obj)
    {




        mAssetManager.load (fileName,obj);

    }




    public static void DoCompliteLoading()
    {
        mAssetManager.finishLoading();
    }



}
