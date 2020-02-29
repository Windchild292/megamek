/*
 *  MegaMek - Copyright (C) 2003, 2004 Ben Mazur (bmazur@sev.org)
 *  Copyright © 2014 Nicholas Walczak (walczak@cs.umn.edu)
 *  Copyright (c) 2020 - The MegaMek Team
 *
 *  This program is free software; you can redistribute it and/or modify it
 *  under the terms of the GNU General Public License as published by the Free
 *  Software Foundation; either version 2 of the License, or (at your option)
 *  any later version.
 *
 *  This program is distributed in the hope that it will be useful, but
 *  WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 *  or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 *  for more details.
 */
package megamek.test;

import megamek.MegaMek;
import megamek.client.ui.swing.HexTileset;
import megamek.common.IBoard;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;

/**
 * This program was designed to test the idea of turning an image into a board
 * file for MegaMek. It takes an image, and then cuts the image the image into
 * hex-sized bits and saves each of those hex images as a file. It also
 * generates a board file with each hex image mapped to a fluff number for the
 * hex it belongs to. The fluff numbers are also written to a file that can be
 * added to a tileSet file.
 *
 * This program really isn't complete, so many of the parameters are just hard
 * coded. The basic premise works, although it needs more refinement. I also
 * think that using terrain fluff to map an image to each hex is kind of an
 * abuse of the framework.
 *
 * @author arlith
 */
public class ImageToBoard {
    boolean loaded = false;
    int hexCols = 41;
    int hexRows = 51;
    int colOffset = 6;
    int rowOffset = 12;

    BufferedImage src, hexTemplate;

    String outputDir;

    public ImageToBoard(String inPath, String outDir) {
        outputDir = outDir;
        try {
            src = ImageIO.read(new File(inPath));
            hexTemplate = ImageIO.read(new File("data/images/misc/hex_filled.png"));
        } catch (IOException e) {
            MegaMek.getLogger().error(ImageToBoard.class, "ImageToBoard Standard Constructor",
                    e.toString());
            return;
        }
        loaded = true;
    }

    public void process() {
        if (!loaded) {
            return;
        }
        BufferedImage hexImg = new BufferedImage(HexTileset.HEX_W, HexTileset.HEX_H,
                BufferedImage.TYPE_INT_ARGB);

        int black = (255 << 8) & (255 << 16) & (255);
        int transparent = 0;
        Graphics hexGraphics = hexImg.getGraphics();
        int width = src.getWidth() - colOffset;
        int height = src.getHeight() - rowOffset;
        int mapHexHeight = height / hexRows;
        double tmp = Math.sin(Math.PI / 6) * (mapHexHeight);
        int mapHexWidth = (int) (width / (mapHexHeight + tmp));
        int mapHexSpacing = width / hexCols;

        try (Writer fw1 = new FileWriter(new File(outputDir, "new.tileset"));
             Writer tilesetOut = new BufferedWriter(fw1);
             Writer fw2 = new FileWriter(new File(outputDir, "new" + IBoard.BOARD_FILE_EXTENSION));
             Writer boardOut = new BufferedWriter(fw2)) {

            boardOut.write("size " + hexCols + " " + hexRows + "\n");

            for (int col = 0; col < hexCols; col++) {
                for (int row = 0; row < hexRows; row++) {
                    int x = colOffset + col * mapHexSpacing;
                    int y = row * mapHexHeight;
                    if ((x % 2) == 1) {
                        y -= mapHexHeight/2;
                    }
                    if (((x + mapHexWidth) > width) || ((y + mapHexHeight) > height) || (y < 0)) {
                        continue;
                    }

                    BufferedImage hexROI = src.getSubimage(x, y, mapHexWidth, mapHexHeight);
                    hexGraphics.drawImage(hexROI, 0, 0, HexTileset.HEX_W, HexTileset.HEX_H, null);
                    for (int i = 0; i < HexTileset.HEX_W; i++) {
                        for (int j = 0; j < HexTileset.HEX_H; j++) {
                            if (hexTemplate.getRGB(i, j) == black) {
                                hexImg.setRGB(i, j, transparent);
                            }
                        }
                    }
                    String colName = String.format("%1$02d", col);
                    String rowName = String.format("%1$02d", row);
                    String fileName = "hexImage" + colName + rowName + ".png";
                    try {
                        String terrName = colName + rowName;
                        File outFile = new File(outputDir, fileName);
                        ImageIO.write(hexImg, "PNG", outFile);
                        tilesetOut.write("super * \"fluff:99" + terrName
                                + "\" \"\" \"tmp/" + fileName + "\"\n");
                        colName = String.format("%1$02d", col+1);
                        rowName = String.format("%1$02d", row+1);
                        terrName = colName + rowName;
                        boardOut.write("hex " + terrName + " 0 \"fluff:99"
                                + terrName + "\" \"\"\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                        return;
                    }
                }
            }
        } catch (IOException e) {
            MegaMek.getLogger().error(ImageToBoard.class, "process", e.toString());
        }
    }
}
