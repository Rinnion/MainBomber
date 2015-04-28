package com.me.Players;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.me.ObjectMaskHelper.Vector2I;
import com.me.Utility.DelayTimer;
import com.me.assetloader.AssetLoader;
import com.me.bomb.BombType;
import com.me.minebomber.Settings;


/**
 * Created by alekseev on 23.09.2014.
 */
public class ArsenalInfo {

    static TextureAtlas atlas = null;
    static Pixmap pixmap;
    static Texture texture;
    static TextureRegion textureRegion;
    static Sprite spriteRegion;
    static Sprite sprite;
    final int stepX = 1;
    final int stepY = 1;
    final Vector2I activBox = new Vector2I(20, 20);
    final Vector2I deactivatedBox = new Vector2I(10, 10);
    final Vector2I pixMapSize = new Vector2I(activBox.x + deactivatedBox.x * 2, activBox.y);
    public boolean isVisible = false;
    AbstractPlayer owner;
    Vector2I leftBoxCenter;
    Vector2I centerBoxCenter;
    Vector2I rightBoxCenter;
    float alpha = 1;
    boolean beginDoItInvisible = false;
    private DelayTimer mFlashTimer = new DelayTimer(100);
    private DelayTimer mDisableTimeout = new DelayTimer(1000, false);


    public ArsenalInfo(AbstractPlayer owner) {
        if (true) {
            atlas = AssetLoader.GetAtlas(Settings.BOMB_DYNAMITE);

            textureRegion = atlas.findRegion(BombType.ATLAS[0]);

            spriteRegion = new Sprite(textureRegion);

            spriteRegion.setRegion(textureRegion);

            spriteRegion.setSize(16, 16);

            spriteRegion.flip(false, true);

            createPixmapPanel();

            texture = new Texture(pixmap);

            sprite = new Sprite(texture);
            sprite.flip(false, true);
        } else {
            leftBoxCenter = new Vector2I(deactivatedBox.x / 2, pixMapSize.y / 2);
            centerBoxCenter = new Vector2I(pixMapSize.x / 2, pixMapSize.y / 2);
            rightBoxCenter = new Vector2I((pixMapSize.x - deactivatedBox.x / 2), pixMapSize.y / 2);
        }
        this.owner = owner;

    }

    private void createPixmapPanel() {

        pixmap = new Pixmap(pixMapSize.x, pixMapSize.y, Pixmap.Format.RGBA8888);

        pixmap.setColor(Color.rgba8888(0.158f, 0.235f, 0.255f, 0.5f));
        //pixmap.fill();

        leftBoxCenter = new Vector2I(deactivatedBox.x / 2, pixMapSize.y / 2);
        centerBoxCenter = new Vector2I(pixMapSize.x / 2, pixMapSize.y / 2);
        rightBoxCenter = new Vector2I((pixMapSize.x - deactivatedBox.x / 2), pixMapSize.y / 2);

        pixmap.fillRectangle(leftBoxCenter.x - deactivatedBox.x / 2, leftBoxCenter.y - deactivatedBox.y / 2, deactivatedBox.x, deactivatedBox.y);
        pixmap.fillRectangle(rightBoxCenter.x - deactivatedBox.x / 2, rightBoxCenter.y - deactivatedBox.y / 2, deactivatedBox.x, deactivatedBox.y);
        //pixmap.fillRectangle(pixMapSize.x- ,stepY,deactivatedBox.x,deactivatedBox.y);


        pixmap.setColor(Color.rgba8888(0.7f, 0.7f, 0.7f, 1));
        pixmap.fillRectangle(centerBoxCenter.x - activBox.x / 2, centerBoxCenter.y - activBox.y / 2, activBox.x, activBox.y);

        //pixmap.setColor(Color.rgba8888 (0.5f,0.5f,0.5f,1));
        //pixmap.drawRectangle(pW-1,0,pW+1,pH);
        //pixmap.drawRectangle(0,0,pixmap.getWidth(),pixmap.getHeight());
    }

    public void DoItVisible() {
        alpha = 1;
        beginDoItInvisible = false;
        mDisableTimeout.Restart();

        isVisible = true;
    }


    public void Render(Batch batch) {
        if (isVisible == false)
            return;

        int x = (int) owner.X;
        int y = (int) owner.Y;
        //FIXME: magic constants :)
        int h = 32;
        int w = 32;
        x = (x + w / 2) - pixmap.getWidth() / 2;


        if (y - pixMapSize.y <= 0)
            y += h;
        else
            y -= pixMapSize.y + 5;

        sprite.setPosition(x, y);

        sprite.draw(batch, alpha);

        if (mDisableTimeout.CheckTimeOut()) {
            beginDoItInvisible = true;
        }


        int index1 = owner.arsenal.getFirstWeapon(owner.arsenal.sindex - 1);
        int index2 = owner.arsenal.getFirstWeapon(owner.arsenal.sindex);
        int index3 = owner.arsenal.getFirstWeapon(owner.arsenal.sindex + 1);


        if ((index1 != -1) && (index1 != index2) && (index1 != index3))
            drawElement(batch, x + leftBoxCenter.x, y + leftBoxCenter.y, index1, 8, 8);
        if (index2 != -1)
            drawElement(batch, x + centerBoxCenter.x, y + centerBoxCenter.y, index2, 10, 10);
        if ((index3 != -1) && (index3 != index2) && (index3 != index1))
            drawElement(batch, x + rightBoxCenter.x, y + rightBoxCenter.y, index3, 8, 8);


        if (mFlashTimer.CheckTimeOut()) {
            if (beginDoItInvisible) {
                alpha -= 0.05f;
                if (alpha < 0) {
                    alpha = 0;
                    isVisible = true;
                }
            }

        }

        /**/

        //batch.draw(texture,x-texture.getWidth()/2,y-10,0.5f );

    }

    private void drawElement(Batch batch, int x, int y, int type, int w, int h) {

        textureRegion = atlas.findRegion(BombType.ATLAS[type]);
        spriteRegion.setRegion(textureRegion);
        spriteRegion.flip(false, true);
        spriteRegion.setSize(w, h);

        spriteRegion.setPosition(x - w / 2, y - h / 2);
        spriteRegion.draw(batch, alpha);
    }


}
