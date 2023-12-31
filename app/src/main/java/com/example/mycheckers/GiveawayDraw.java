package com.example.mycheckers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

public class GiveawayDraw extends View {
    Bitmap wc1 = BitmapFactory.decodeResource(getResources(), R.drawable.whitechecker);
    Bitmap bc1 = BitmapFactory.decodeResource(getResources(), R.drawable.blackchecker);
    Bitmap wq1 = BitmapFactory.decodeResource(getResources(), R.drawable.whitequeen);
    Bitmap bq1 = BitmapFactory.decodeResource(getResources(), R.drawable.blackqueen);
    Bitmap bs1 = BitmapFactory.decodeResource(getResources(), R.drawable.blacksquare);
    Bitmap wc, bc, wq, bq, bs;
    Square[][] squares = new Square[8][8];
    float touchX, touchY;
    int side, check2, flag, o, q, k, l, m, n, r, t, check, check1, check3, mf, fa, cw, cb, check4 = 0;
    int s1 = -1, s2 = -1, s3 = -1, s4 = -1, s5 = -1, s6 = -1, s7 = -1, s8 = -1;
    int who = 1;

    public GiveawayDraw(Context context) {
        super(context);
        for (int i = 0; i < squares.length; i++) {
            for (int j = 0; j < squares[i].length; j++) {
                if ((i + 1) % 2 == 0) {
                    switch (j) {
                        case 0:
                        case 2:
                        case 4:
                        case 6:
                            squares[i][j] = new Square(null, 0, 0, 3, false, false, 0, false);
                            break;
                        case 1:
                            squares[i][j] = new Square(wc1, i + 1, j + 1, 1, false, false, 0, false);
                            break;
                        case 3:
                            squares[i][j] = new Square(bs1, i + 1, j + 1, 0, false, false, 0, false);
                            break;
                        case 5:
                        case 7:
                            squares[i][j] = new Square(bc1, i + 1, j + 1, 2, false, false, 0, false);
                            break;
                    }
                } else {
                    switch (j) {
                        case 1:
                        case 3:
                        case 5:
                        case 7:
                            squares[i][j] = new Square(null, 0, 0, 3, false, false, 0, false);
                            break;
                        case 0:
                        case 2:
                            squares[i][j] = new Square(wc1, i + 1, j + 1, 1, false, false, 0, false);
                            break;
                        case 4:
                            squares[i][j] = new Square(bs1, i + 1, j + 1, 0, false, false, 0, false);
                            break;
                        case 6:
                            squares[i][j] = new Square(bc1, i + 1, j + 1, 2, false, false, 0, false);
                            break;
                    }
                }
            }
        }
    }

    Paint p = new Paint();
    Paint p1 = new Paint();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        side = getWidth() / 8;
        if (check4 == 0) {
            wc = Bitmap.createScaledBitmap(wc1, side, side, false);
            bc = Bitmap.createScaledBitmap(bc1, side, side, false);
            wq = Bitmap.createScaledBitmap(wq1, side, side, false);
            bq = Bitmap.createScaledBitmap(bq1, side, side, false);
            bs = Bitmap.createScaledBitmap(bs1, side, side, false);
        }
        p.setColor(Color.WHITE);
        canvas.drawRect(0, 0, getWidth(), getHeight(), p);
        int x = 0, y = getHeight() / 2 - getWidth() / 2, a = 0;
        float x0 = x, y0 = y;
        /*рисование доски*/
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                canvas.drawRect(x, y, x + (float) getWidth() / 8, y + (float) getWidth() / 8, p);
                a++;
                p.setColor(a % 2 == 0 ? Color.WHITE : Color.rgb(128, 128, 128));
                x += getWidth() / 8;
            }
            x = 0;
            y += getWidth() / 8;
            p.setColor(a % 2 == 1 ? Color.WHITE : Color.rgb(128, 128, 128));
            a--;
        }
        p.setColor(Color.rgb(128, 128, 128));
        p.setStyle(Paint.Style.STROKE);
        canvas.drawRect(x0, y0, getWidth() - x, y, p);
        /*начальная расстановка шашек*/
        for (int i = 0; i < squares.length; i++) {
            for (int j = 0; j < squares[i].length; j++) {
                if (i % 2 == j % 2) {
                    if (squares[i][j].getB() == wc1) squares[i][j].setB(wc);
                    if (squares[i][j].getB() == bc1) squares[i][j].setB(bc);
                    if (squares[i][j].getB() == bs1) squares[i][j].setB(bs);
                    spawn(canvas, squares[i][j]);
                }
            }
        }
        p.setStyle(Paint.Style.FILL);
        p.setTextSize(80f);
        p.setColor(Color.BLACK);
        p1.setColor(Color.BLACK);
        p1.setTextSize(getWidth() / 10);
        if (check4 == 0)
            canvas.drawText("Ход белых", getWidth() / 4, getHeight() / 2 + getWidth() / 2 + 100, p);
        check4++;
        for (int i = 0; i < squares.length; i++) {
            for (int j = 0; j < squares[i].length; j++) {
                if (i % 2 == j % 2 && squares[i][j].isAllow() && squares[i][j].isD()) {
                    for (int k = 0; k < squares.length; k++) {
                        for (int l = 0; l < squares[i].length; l++) {
                            if (squares[k][l].isAllow() && k == i && l == j) {
                                squares[k][l].setAllow(false);
                            }
                        }
                    }
                    if (check1 == 1) {
                        if (!squares[i][j].isQueen())
                            switch (j) {
                                case 2:
                                case 3:
                                case 4:
                                case 5:
                                    if (squares[i][j].getColor() == 1 && who == 1) {
                                        if (i >= 2 && squares[i - 2][j + 2].getColor() == 0 && squares[i - 1][j + 1].getColor() == 2) {
                                            drawAllow(canvas, squares[i - 2][j + 2], p);
                                            mf = 1;
                                        }
                                        if (i <= 5 && squares[i + 2][j + 2].getColor() == 0 && squares[i + 1][j + 1].getColor() == 2) {
                                            drawAllow(canvas, squares[i + 2][j + 2], p);
                                            mf = 1;
                                        }
                                        if (i >= 2 && squares[i - 2][j - 2].getColor() == 0 && squares[i - 1][j - 1].getColor() == 2) {
                                            drawAllow(canvas, squares[i - 2][j - 2], p);
                                            mf = 1;
                                        }
                                        if (i <= 5 && squares[i + 2][j - 2].getColor() == 0 && squares[i + 1][j - 1].getColor() == 2) {
                                            drawAllow(canvas, squares[i + 2][j - 2], p);
                                            mf = 1;
                                        }
                                        if (i >= 1 && squares[i - 1][j + 1].getColor() == 0 && mf == 0)
                                            drawAllow(canvas, squares[i - 1][j + 1], p);
                                        if (i <= 6 && squares[i + 1][j + 1].getColor() == 0 && mf == 0)
                                            drawAllow(canvas, squares[i + 1][j + 1], p);
                                    } else if (squares[i][j].getColor() == 2 && who == 2) {
                                        if (i >= 2 && squares[i - 2][j - 2].getColor() == 0 && squares[i - 1][j - 1].getColor() == 1) {
                                            drawAllow(canvas, squares[i - 2][j - 2], p);
                                            mf = 1;
                                        }
                                        if (i <= 5 && squares[i + 2][j - 2].getColor() == 0 && squares[i + 1][j - 1].getColor() == 1) {
                                            drawAllow(canvas, squares[i + 2][j - 2], p);
                                            mf = 1;
                                        }
                                        if (i >= 2 && squares[i - 2][j + 2].getColor() == 0 && squares[i - 1][j + 1].getColor() == 1) {
                                            drawAllow(canvas, squares[i - 2][j + 2], p);
                                            mf = 1;
                                        }
                                        if (i <= 5 && squares[i + 2][j + 2].getColor() == 0 && squares[i + 1][j + 1].getColor() == 1) {
                                            drawAllow(canvas, squares[i + 2][j + 2], p);
                                            mf = 1;
                                        }
                                        if (i >= 1 && squares[i - 1][j - 1].getColor() == 0 && mf == 0)
                                            drawAllow(canvas, squares[i - 1][j - 1], p);
                                        if (i <= 6 && squares[i + 1][j - 1].getColor() == 0 && mf == 0)
                                            drawAllow(canvas, squares[i + 1][j - 1], p);
                                    }
                                    break;
                                case 0:
                                    if (squares[i][j].getColor() == 1 && who == 1) {
                                        if (i >= 2 && squares[i - 2][j + 2].getColor() == 0 && squares[i - 1][j + 1].getColor() == 2) {
                                            drawAllow(canvas, squares[i - 2][j + 2], p);
                                            mf = 1;
                                        }
                                        if (i <= 5 && squares[i + 2][j + 2].getColor() == 0 && squares[i + 1][j + 1].getColor() == 2) {
                                            drawAllow(canvas, squares[i + 2][j + 2], p);
                                            mf = 1;
                                        }
                                        if (i >= 1 && squares[i - 1][j + 1].getColor() == 0 && mf == 0)
                                            drawAllow(canvas, squares[i - 1][j + 1], p);
                                        if (i <= 6 && squares[i + 1][j + 1].getColor() == 0 && mf == 0)
                                            drawAllow(canvas, squares[i + 1][j + 1], p);
                                    } else if (squares[i][j].getColor() == 2 && who == 2) {
                                        if (i >= 2 && squares[i - 2][j + 2].getColor() == 0 && squares[i - 1][j + 1].getColor() == 1)
                                            drawAllow(canvas, squares[i - 2][j + 2], p);
                                        if (i <= 5 && squares[i + 2][j + 2].getColor() == 0 && squares[i + 1][j + 1].getColor() == 1)
                                            drawAllow(canvas, squares[i + 2][j + 2], p);
                                    }
                                    break;
                                case 1:
                                    if (squares[i][j].getColor() == 1 && who == 1) {
                                        if (i >= 2 && squares[i - 2][j + 2].getColor() == 0 && squares[i - 1][j + 1].getColor() == 2) {
                                            drawAllow(canvas, squares[i - 2][j + 2], p);
                                            mf = 1;
                                        }
                                        if (i <= 5 && squares[i + 2][j + 2].getColor() == 0 && squares[i + 1][j + 1].getColor() == 2) {
                                            drawAllow(canvas, squares[i + 2][j + 2], p);
                                            mf = 1;
                                        }
                                        if (squares[i - 1][j + 1].getColor() == 0 && mf == 0)
                                            drawAllow(canvas, squares[i - 1][j + 1], p);
                                        if (i <= 6 && squares[i + 1][j + 1].getColor() == 0 && mf == 0)
                                            drawAllow(canvas, squares[i + 1][j + 1], p);
                                    } else if (squares[i][j].getColor() == 2 && who == 2) {
                                        if (i >= 2 && squares[i - 2][j + 2].getColor() == 0 && squares[i - 1][j + 1].getColor() == 1) {
                                            drawAllow(canvas, squares[i - 2][j + 2], p);
                                            mf = 1;
                                        }
                                        if (i <= 5 && squares[i + 2][j + 2].getColor() == 0 && squares[i + 1][j + 1].getColor() == 1) {
                                            drawAllow(canvas, squares[i + 2][j + 2], p);
                                            mf = 1;
                                        }
                                        if (squares[i - 1][j - 1].getColor() == 0 && mf == 0)
                                            drawAllow(canvas, squares[i - 1][j - 1], p);
                                        if (i <= 6 && squares[i + 1][j - 1].getColor() == 0 && mf == 0)
                                            drawAllow(canvas, squares[i + 1][j - 1], p);
                                    }
                                    break;
                                case 6:
                                    if (squares[i][j].getColor() == 1 && who == 1) {
                                        if (i >= 2 && squares[i - 2][j - 2].getColor() == 0 && squares[i - 1][j - 1].getColor() == 2) {
                                            drawAllow(canvas, squares[i - 2][j - 2], p);
                                            mf = 1;
                                        }
                                        if (i <= 5 && squares[i + 2][j - 2].getColor() == 0 && squares[i + 1][j - 1].getColor() == 2) {
                                            drawAllow(canvas, squares[i + 2][j - 2], p);
                                            mf = 1;
                                        }
                                        if (i >= 1 && squares[i - 1][j + 1].getColor() == 0 && mf == 0)
                                            drawAllow(canvas, squares[i - 1][j + 1], p);
                                        if (i <= 6 && squares[i + 1][j + 1].getColor() == 0 && mf == 0)
                                            drawAllow(canvas, squares[i + 1][j + 1], p);
                                    } else if (squares[i][j].getColor() == 2 && who == 2) {
                                        if (i >= 2 && squares[i - 2][j - 2].getColor() == 0 && squares[i - 1][j - 1].getColor() == 1) {
                                            drawAllow(canvas, squares[i - 2][j - 2], p);
                                            mf = 1;
                                        }
                                        if (i <= 5 && squares[i + 2][j - 2].getColor() == 0 && squares[i + 1][j - 1].getColor() == 1) {
                                            drawAllow(canvas, squares[i + 2][j - 2], p);
                                            mf = 1;
                                        }
                                        if (i >= 1 && squares[i - 1][j - 1].getColor() == 0 && mf == 0)
                                            drawAllow(canvas, squares[i - 1][j - 1], p);
                                        if (i <= 6 && squares[i + 1][j - 1].getColor() == 0 && mf == 0)
                                            drawAllow(canvas, squares[i + 1][j - 1], p);
                                    }
                                    break;
                                case 7:
                                    if (squares[i][j].getColor() == 1 && who == 1) {
                                        if (i >= 2 && squares[i - 2][j - 2].getColor() == 0 && squares[i - 1][j - 1].getColor() == 2)
                                            drawAllow(canvas, squares[i - 2][j - 2], p);
                                        if (i <= 5 && squares[i + 2][j - 2].getColor() == 0 && squares[i + 1][j - 1].getColor() == 2)
                                            drawAllow(canvas, squares[i + 2][j - 2], p);
                                    } else if (squares[i][j].getColor() == 2 && who == 2) {
                                        if (i >= 2 && squares[i - 2][j - 2].getColor() == 0 && squares[i - 1][j - 1].getColor() == 1) {
                                            drawAllow(canvas, squares[i - 2][j - 2], p);
                                            mf = 1;
                                        }
                                        if (i <= 5 && squares[i + 2][j - 2].getColor() == 0 && squares[i + 1][j - 1].getColor() == 1) {
                                            drawAllow(canvas, squares[i + 2][j - 2], p);
                                            mf = 1;
                                        }
                                        if (squares[i - 1][j - 1].getColor() == 0 && mf == 0)
                                            drawAllow(canvas, squares[i - 1][j - 1], p);
                                        if (i <= 6 && squares[i + 1][j - 1].getColor() == 0 && mf == 0)
                                            drawAllow(canvas, squares[i + 1][j - 1], p);
                                    }
                                    break;
                            }
                        else if (check == 0 && squares[i][j].getColor() == who) {
                            r = squares[i][j].getColor();
                            m = i;
                            n = j;
                            while (m < 6 && n < 6) {
                                if (squares[m + 1][n + 1].getColor() == r || squares[m + 1][n + 1].getColor() != 0 && squares[m + 2][n + 2].getColor() != 0)
                                    break;
                                if (squares[m + 1][n + 1].getColor() != r && squares[m + 1][n + 1].getColor() != 0) {
                                    l++;
                                    if (l >= 2) break;
                                }
                                if (squares[m + 1][n + 1].getColor() != r && squares[m + 1][n + 1].getColor() != 0 && squares[m + 2][n + 2].getColor() == 0) {
                                    s1 = m + 2;
                                    s2 = n + 2;
                                    break;
                                }
                                m++;
                                n++;
                            }
                            l = 0;
                            m = i;
                            n = j;
                            while (m > 1 && n < 6) {
                                if (squares[m - 1][n + 1].getColor() == r || squares[m - 1][n + 1].getColor() != 0 && squares[m - 2][n + 2].getColor() != 0)
                                    break;
                                if (squares[m - 1][n + 1].getColor() != r && squares[m - 1][n + 1].getColor() != 0) {
                                    l++;
                                    if (l >= 2) break;
                                }
                                if (squares[m - 1][n + 1].getColor() != r && squares[m - 1][n + 1].getColor() != 0 && squares[m - 2][n + 2].getColor() == 0) {
                                    s3 = m - 2;
                                    s4 = n + 2;
                                    break;
                                }
                                m--;
                                n++;
                            }
                            l = 0;
                            m = i;
                            n = j;
                            while (m > 1 && n > 1) {
                                if (squares[m - 1][n - 1].getColor() == r || squares[m - 1][n - 1].getColor() != 0 && squares[m - 2][n - 2].getColor() != 0)
                                    break;
                                if (squares[m - 1][n - 1].getColor() != r && squares[m - 1][n - 1].getColor() != 0) {
                                    l++;
                                    if (l >= 2) break;
                                }
                                if (squares[m - 1][n - 1].getColor() != r && squares[m - 1][n - 1].getColor() != 0 && squares[m - 2][n - 2].getColor() == 0) {
                                    s5 = m - 2;
                                    s6 = n - 2;
                                    break;
                                }
                                m--;
                                n--;
                            }
                            l = 0;
                            m = i;
                            n = j;
                            while (m < 6 && n > 1) {
                                if (squares[m + 1][n - 1].getColor() == r || squares[m + 1][n - 1].getColor() != 0 && squares[m + 2][n - 2].getColor() != 0)
                                    break;
                                if (squares[m + 1][n - 1].getColor() != r && squares[m + 1][n - 1].getColor() != 0) {
                                    l++;
                                    if (l >= 2) break;
                                }
                                if (squares[m + 1][n - 1].getColor() != r && squares[m + 1][n - 1].getColor() != 0 && squares[m + 2][n - 2].getColor() == 0) {
                                    s7 = m + 2;
                                    s8 = n - 2;
                                    break;
                                }
                                m++;
                                n--;
                            }
                            l = 0;
                            r = squares[i][j].getColor();
                            m = i;
                            n = j;
                            while (m < 6 && n < 6) {
                                if (squares[m + 1][n + 1].getColor() == r || squares[m + 1][n + 1].getColor() != 0 && squares[m + 2][n + 2].getColor() != 0)
                                    break;
                                if (squares[m + 1][n + 1].getColor() != r && squares[m + 1][n + 1].getColor() != 0) {
                                    l++;
                                    if (l >= 2) break;
                                }
                                if (squares[m + 1][n + 1].getColor() != r && squares[m + 1][n + 1].getColor() != 0 && squares[m + 2][n + 2].getColor() == 0) {
                                    mf = 1;
                                    break;
                                }
                                m++;
                                n++;
                            }
                            l = 0;
                            m = i;
                            n = j;
                            while (m > 1 && n < 6 && mf == 0) {
                                if (squares[m - 1][n + 1].getColor() == r || squares[m - 1][n + 1].getColor() != 0 && squares[m - 2][n + 2].getColor() != 0)
                                    break;
                                if (squares[m - 1][n + 1].getColor() != r && squares[m - 1][n + 1].getColor() != 0) {
                                    l++;
                                    if (l >= 2) break;
                                }
                                if (squares[m - 1][n + 1].getColor() != r && squares[m - 1][n + 1].getColor() != 0 && squares[m - 2][n + 2].getColor() == 0) {
                                    mf = 1;
                                    break;
                                }
                                m--;
                                n++;
                            }
                            l = 0;
                            m = i;
                            n = j;
                            while (m > 1 && n > 1 && mf == 0) {
                                if (squares[m - 1][n - 1].getColor() == r || squares[m - 1][n - 1].getColor() != 0 && squares[m - 2][n - 2].getColor() != 0)
                                    break;
                                if (squares[m - 1][n - 1].getColor() != r && squares[m - 1][n - 1].getColor() != 0) {
                                    l++;
                                    if (l >= 2) break;
                                }
                                if (squares[m - 1][n - 1].getColor() != r && squares[m - 1][n - 1].getColor() != 0 && squares[m - 2][n - 2].getColor() == 0) {
                                    mf = 1;
                                    break;
                                }
                                m--;
                                n--;
                            }
                            l = 0;
                            m = i;
                            n = j;
                            while (m < 6 && n > 1 && mf == 0) {
                                if (squares[m + 1][n - 1].getColor() == r || squares[m + 1][n - 1].getColor() != 0 && squares[m + 2][n - 2].getColor() != 0)
                                    break;
                                if (squares[m + 1][n - 1].getColor() != r && squares[m + 1][n - 1].getColor() != 0) {
                                    l++;
                                    if (l >= 2) break;
                                }
                                if (squares[m + 1][n - 1].getColor() != r && squares[m + 1][n - 1].getColor() != 0 && squares[m + 2][n - 2].getColor() == 0) {
                                    mf = 1;
                                    break;
                                }
                                m++;
                                n--;
                            }
                            l = 0;
                            if (i + 1 < 8 && j + 1 < 8)
                                if (squares[i + 1][j + 1].getColor() == 0) {
                                    if (mf == 0 || i + 1 >= s1 && j + 1 >= s2 && s1 != -1 && s2 != -1)
                                        drawAllow(canvas, squares[i + 1][j + 1], p);
                                } else if (squares[i + 1][j + 1].getColor() == squares[i][j].getColor())
                                    k++;
                                else l++;
                            if (i + 2 < 8 && j + 2 < 8)
                                if (squares[i + 2][j + 2].getColor() == 0 && k == 0) {
                                    if (mf == 0 || i + 2 >= s1 && j + 2 >= s2 && s1 != -1 && s2 != -1)
                                        drawAllow(canvas, squares[i + 2][j + 2], p);
                                } else if (squares[i + 2][j + 2].getColor() == squares[i][j].getColor())
                                    k++;
                                else l++;
                            if (i + 3 < 8 && j + 3 < 8)
                                if (squares[i + 3][j + 3].getColor() == 0 && k == 0 && l < 2) {
                                    if (mf == 0 || i + 3 >= s1 && j + 3 >= s2 && s1 != -1 && s2 != -1)
                                        drawAllow(canvas, squares[i + 3][j + 3], p);
                                } else if (squares[i + 3][j + 3].getColor() == squares[i][j].getColor())
                                    k++;
                                else l++;
                            if (i + 4 < 8 && j + 4 < 8)
                                if (squares[i + 4][j + 4].getColor() == 0 && k == 0 && l < 2) {
                                    if (mf == 0 || i + 4 >= s1 && j + 4 >= s2 && s1 != -1 && s2 != -1)
                                        drawAllow(canvas, squares[i + 4][j + 4], p);
                                } else if (squares[i + 4][j + 4].getColor() == squares[i][j].getColor())
                                    k++;
                                else l++;
                            if (i + 5 < 8 && j + 5 < 8)
                                if (squares[i + 5][j + 5].getColor() == 0 && k == 0 && l < 2) {
                                    if (mf == 0 || i + 5 >= s1 && j + 5 >= s2 && s1 != -1 && s2 != -1)
                                        drawAllow(canvas, squares[i + 5][j + 5], p);
                                } else if (squares[i + 5][j + 5].getColor() == squares[i][j].getColor())
                                    k++;
                                else l++;
                            if (i + 6 < 8 && j + 6 < 8)
                                if (squares[i + 6][j + 6].getColor() == 0 && k == 0 && l < 2) {
                                    if (mf == 0 || i + 6 >= s1 && j + 6 >= s2 && s1 != -1 && s2 != -1)
                                        drawAllow(canvas, squares[i + 6][j + 6], p);
                                } else if (squares[i + 6][j + 6].getColor() == squares[i][j].getColor())
                                    k++;
                                else l++;
                            if (i + 7 < 8 && j + 7 < 8)
                                if (squares[i + 7][j + 7].getColor() == 0 && k == 0 && l < 2)
                                    if (mf == 0 || i + 7 >= s1 && j + 7 >= s2 && s1 != -1 && s2 != -1)
                                        drawAllow(canvas, squares[i + 7][j + 7], p);
                            k = 0;
                            l = 0;
                            if (i - 1 > -1 && j + 1 < 8)
                                if (squares[i - 1][j + 1].getColor() == 0) {
                                    if (mf == 0 || i - 1 <= s3 && j + 1 >= s4 && s3 != -1 && s4 != -1)
                                        drawAllow(canvas, squares[i - 1][j + 1], p);
                                } else if (squares[i - 1][j + 1].getColor() == squares[i][j].getColor())
                                    k++;
                                else l++;
                            if (i - 2 > -1 && j + 2 < 8)
                                if (squares[i - 2][j + 2].getColor() == 0 && k == 0) {
                                    if (mf == 0 || i - 2 <= s3 && j + 2 >= s4 && s3 != -1 && s4 != -1)
                                        drawAllow(canvas, squares[i - 2][j + 2], p);
                                } else if (squares[i - 2][j + 2].getColor() == squares[i][j].getColor())
                                    k++;
                                else l++;
                            if (i - 3 > -1 && j + 3 < 8)
                                if (squares[i - 3][j + 3].getColor() == 0 && k == 0 && l < 2) {
                                    if (mf == 0 || i - 3 <= s3 && j + 3 >= s4 && s3 != -1 && s4 != -1)
                                        drawAllow(canvas, squares[i - 3][j + 3], p);
                                } else if (squares[i - 3][j + 3].getColor() == squares[i][j].getColor())
                                    k++;
                                else l++;
                            if (i - 4 > -1 && j + 4 < 8)
                                if (squares[i - 4][j + 4].getColor() == 0 && k == 0 && l < 2) {
                                    if (mf == 0 || i - 4 <= s3 && j + 4 >= s4 && s3 != -1 && s4 != -1)
                                        drawAllow(canvas, squares[i - 4][j + 4], p);
                                } else if (squares[i - 4][j + 4].getColor() == squares[i][j].getColor())
                                    k++;
                                else l++;
                            if (i - 5 > -1 && j + 5 < 8)
                                if (squares[i - 5][j + 5].getColor() == 0 && k == 0 && l < 2) {
                                    if (mf == 0 || i - 5 <= s3 && j + 5 >= s4 && s3 != -1 && s4 != -1)
                                        drawAllow(canvas, squares[i - 5][j + 5], p);
                                } else if (squares[i - 5][j + 5].getColor() == squares[i][j].getColor())
                                    k++;
                                else l++;
                            if (i - 6 > -1 && j + 6 < 8)
                                if (squares[i - 6][j + 6].getColor() == 0 && k == 0 && l < 2) {
                                    if (mf == 0 || i - 6 <= s3 && j + 6 >= s4 && s3 != -1 && s4 != -1)
                                        drawAllow(canvas, squares[i - 6][j + 6], p);
                                } else if (squares[i - 6][j + 6].getColor() == squares[i][j].getColor())
                                    k++;
                                else l++;
                            if (i - 7 > -1 && j + 7 < 8)
                                if (squares[i - 7][j + 7].getColor() == 0 && k == 0 && l < 2)
                                    if (mf == 0 || i - 7 <= s3 && j + 7 >= s4 && s3 != -1 && s4 != -1)
                                        drawAllow(canvas, squares[i - 7][j + 7], p);
                            k = 0;
                            l = 0;
                            if (i - 1 > -1 && j - 1 > -1)
                                if (squares[i - 1][j - 1].getColor() == 0) {
                                    if (mf == 0 || i - 1 <= s5 && j - 1 <= s6 && s5 != -1 && s6 != -1)
                                        drawAllow(canvas, squares[i - 1][j - 1], p);
                                } else if (squares[i - 1][j - 1].getColor() == squares[i][j].getColor())
                                    k++;
                                else l++;
                            if (i - 2 > -1 && j - 2 > -1)
                                if (squares[i - 2][j - 2].getColor() == 0 && k == 0) {
                                    if (mf == 0 || i - 2 <= s5 && j - 2 <= s6 && s5 != -1 && s6 != -1)
                                        drawAllow(canvas, squares[i - 2][j - 2], p);
                                } else if (squares[i - 2][j - 2].getColor() == squares[i][j].getColor())
                                    k++;
                                else l++;
                            if (i - 3 > -1 && j - 3 > -1)
                                if (squares[i - 3][j - 3].getColor() == 0 && k == 0 && l < 2) {
                                    if (mf == 0 || i - 3 <= s5 && j - 3 <= s6 && s5 != -1 && s6 != -1)
                                        drawAllow(canvas, squares[i - 3][j - 3], p);
                                } else if (squares[i - 3][j - 3].getColor() == squares[i][j].getColor())
                                    k++;
                                else l++;
                            if (i - 4 > -1 && j - 4 > -1)
                                if (squares[i - 4][j - 4].getColor() == 0 && k == 0 && l < 2) {
                                    if (mf == 0 || i - 4 <= s5 && j - 4 <= s6 && s5 != -1 && s6 != -1)
                                        drawAllow(canvas, squares[i - 4][j - 4], p);
                                } else if (squares[i - 4][j - 4].getColor() == squares[i][j].getColor())
                                    k++;
                                else l++;
                            if (i - 5 > -1 && j - 5 > -1)
                                if (squares[i - 5][j - 5].getColor() == 0 && k == 0 && l < 2) {
                                    if (mf == 0 || i - 5 <= s5 && j - 5 <= s6 && s5 != -1 && s6 != -1)
                                        drawAllow(canvas, squares[i - 5][j - 5], p);
                                } else if (squares[i - 5][j - 5].getColor() == squares[i][j].getColor())
                                    k++;
                                else l++;
                            if (i - 6 > -1 && j - 6 > -1)
                                if (squares[i - 6][j - 6].getColor() == 0 && k == 0 && l < 2) {
                                    if (mf == 0 || i - 6 <= s5 && j - 6 <= s6 && s5 != -1 && s6 != -1)
                                        drawAllow(canvas, squares[i - 6][j - 6], p);
                                } else if (squares[i - 6][j - 6].getColor() == squares[i][j].getColor())
                                    k++;
                                else l++;
                            if (i - 7 > -1 && j - 7 > -1)
                                if (squares[i - 7][j - 7].getColor() == 0 && k == 0 && l < 2)
                                    if (mf == 0 || i - 7 <= s5 && j - 7 <= s6 && s5 != -1 && s6 != -1)
                                        drawAllow(canvas, squares[i - 7][j - 7], p);
                            k = 0;
                            l = 0;
                            if (i + 1 < 8 && j - 1 > -1)
                                if (squares[i + 1][j - 1].getColor() == 0) {
                                    if (mf == 0 || i + 1 >= s7 && j - 1 <= s8 && s7 != -1 && s8 != -1)
                                        drawAllow(canvas, squares[i + 1][j - 1], p);
                                } else if (squares[i + 1][j - 1].getColor() == squares[i][j].getColor())
                                    k++;
                                else l++;
                            if (i + 2 < 8 && j - 2 > -1)
                                if (squares[i + 2][j - 2].getColor() == 0 && k == 0) {
                                    if (mf == 0 || i + 2 >= s7 && j - 2 <= s8 && s7 != -1 && s8 != -1)
                                        drawAllow(canvas, squares[i + 2][j - 2], p);
                                } else if (squares[i + 2][j - 2].getColor() == squares[i][j].getColor())
                                    k++;
                                else l++;
                            if (i + 3 < 8 && j - 3 > -1)
                                if (squares[i + 3][j - 3].getColor() == 0 && k == 0 && l < 2) {
                                    if (mf == 0 || i + 3 >= s7 && j - 3 <= s8 && s7 != -1 && s8 != -1)
                                        drawAllow(canvas, squares[i + 3][j - 3], p);
                                } else if (squares[i + 3][j - 3].getColor() == squares[i][j].getColor())
                                    k++;
                                else l++;
                            if (i + 4 < 8 && j - 4 > -1)
                                if (squares[i + 4][j - 4].getColor() == 0 && k == 0 && l < 2) {
                                    if (mf == 0 || i + 4 >= s7 && j - 4 <= s8 && s7 != -1 && s8 != -1)
                                        drawAllow(canvas, squares[i + 4][j - 4], p);
                                } else if (squares[i + 4][j - 4].getColor() == squares[i][j].getColor())
                                    k++;
                                else l++;
                            if (i + 5 < 8 && j - 5 > -1)
                                if (squares[i + 5][j - 5].getColor() == 0 && k == 0 && l < 2) {
                                    if (mf == 0 || i + 5 >= s7 && j - 5 <= s8 && s7 != -1 && s8 != -1)
                                        drawAllow(canvas, squares[i + 5][j - 5], p);
                                } else if (squares[i + 5][j - 5].getColor() == squares[i][j].getColor())
                                    k++;
                                else l++;
                            if (i + 6 < 8 && j - 6 > -1)
                                if (squares[i + 6][j - 6].getColor() == 0 && k == 0 && l < 2) {
                                    if (mf == 0 || i + 6 >= s7 && j - 6 <= s8 && s7 != -1 && s8 != -1)
                                        drawAllow(canvas, squares[i + 6][j - 6], p);
                                } else if (squares[i + 6][j - 6].getColor() == squares[i][j].getColor())
                                    k++;
                                else l++;
                            if (i + 7 < 8 && j - 7 > -1)
                                if (squares[i + 7][j - 7].getColor() == 0 && k == 0 && l < 2)
                                    if (mf == 0 || i + 7 >= s7 && j - 7 <= s8 && s7 != -1 && s8 != -1)
                                        drawAllow(canvas, squares[i + 7][j - 7], p);
                            k = 0;
                            l = 0;
                            s1 = -1;
                            s2 = -1;
                            s3 = -1;
                            s4 = -1;
                            s5 = -1;
                            s6 = -1;
                            s7 = -1;
                            s8 = -1;
                        }
                        check1 = 0;
                    }
                    check = 0;
                    if (squares[i][j].isQueen()) {
                        if (j == 7 && squares[i][j].getB() == wc) squares[i][j].setB(wq);
                        else if (j == 0 && squares[i][j].getB() == bc) squares[i][j].setB(bq);
                        drawSquare(canvas, squares[i][j], squares[i][j].getB());
                    }
                    if (who == 2) {
                        p.setColor(Color.BLACK);
                        canvas.rotate(180, getWidth() / 2, getHeight() / 2 - getWidth() / 2 - 100);
                        canvas.drawText("Ход чёрных", getWidth() / 4, getHeight() / 2 - getWidth() / 2 - 100, p);
                        canvas.rotate(180, getWidth() / 2, getHeight() / 2 - getWidth() / 2 - 100);
                        p.setColor(Color.WHITE);
                        canvas.drawRect(0, getHeight() / 2 + getWidth() / 2 + 10, getWidth(), getHeight(), p);
                    } else if (who == 1) {
                        p.setColor(Color.BLACK);
                        canvas.drawText("Ход белых", getWidth() / 4, getHeight() / 2 + getWidth() / 2 + 100, p);
                        p.setColor(Color.WHITE);
                        canvas.drawRect(0, 0, getWidth(), getHeight() / 2 - getWidth() / 2, p);
                    }
                    for (Square[] square : squares) {
                        for (int l = 0; l < squares[i].length; l++) {
                            if (square[l].getColor() == 1) cw++;
                            else if (square[l].getColor() == 2) cb++;
                        }
                    }
                    if (cw == 0) {
                        p.setColor(Color.WHITE);
                        canvas.drawRect(0, getHeight() / 2 + getWidth() / 2 + 10, getWidth(), getHeight(), p);
                        p.setColor(Color.RED);
                        canvas.drawText("Белые выиграли", getWidth() / 20, getHeight() / 2 + getWidth() / 2 + 100, p);
                        p.setColor(Color.WHITE);
                        canvas.drawRect(0, 0, getWidth(), getHeight() / 2 - getWidth() / 2, p);
                    }
                    if (cb == 0) {
                        p.setColor(Color.WHITE);
                        canvas.drawRect(0, 0, getWidth(), getHeight() / 2 - getWidth() / 2, p);
                        p.setColor(Color.RED);
                        canvas.rotate(180, getWidth() / 2, getHeight() / 2 - getWidth() / 2 - 100);
                        canvas.drawText("Чёрные выиграли", getWidth() / 20, getHeight() / 2 - getWidth() / 2 - 100, p);
                        canvas.rotate(180, getWidth() / 2, getHeight() / 2 - getWidth() / 2 - 100);
                        p.setColor(Color.WHITE);
                        canvas.drawRect(0, getHeight() / 2 + getWidth() / 2 + 10, getWidth(), getHeight(), p);
                    }
                    cw = 0;
                    cb = 0;
                }
            }
        }
    }

    public void spawn(Canvas canvas, Square s) {
        canvas.drawBitmap(s.getB(), (s.getV() - 1) * getWidth() / 8, getHeight() / 2 - getWidth() / 2 + ((8 - s.getH()) * getWidth() / 8), p);
    }

    public void drawSquare(Canvas canvas, Square s, Bitmap b) {
        s.setB(b);
        if (s.getB() == wc || s.getB() == wq || s.getB() == wq1)
            s.setColor(1);
        else if (s.getB() == bc || s.getB() == bq || s.getB() == bq1)
            s.setColor(2);
        else if (s.getB() == bs)
            s.setColor(0);
        canvas.drawBitmap(b, (s.getV() - 1) * getWidth() / 8, getHeight() / 2 - getWidth() / 2 + ((8 - s.getH()) * getWidth() / 8), p);
    }

    public void drawAllow(Canvas canvas, Square s, Paint p) {
        p.setColor(Color.GREEN);
        canvas.drawRect((s.getV() - 1) * getWidth() / 8, getHeight() / 2 - getWidth() / 2 + ((8 - s.getH()) * getWidth() / 8), s.getV() * getWidth() / 8, getHeight() / 2 - getWidth() / 2 + ((9 - s.getH()) * getWidth() / 8), p);
    }

    public void move(Canvas canvas, Square s1, Square s2, Bitmap b) {
        drawSquare(canvas, s1, b);
        drawSquare(canvas, s2, bs);
        if (who == 1) who = 2;
        else if (who == 2) who = 1;
        for (int i = 0; i < squares.length; i++) {
            for (int j = 0; j < squares[i].length; j++) {
                if (squares[i][j].getColor() == who) {
                    if (!squares[i][j].isQueen()) {
                        r = squares[i][j].getColor();
                        if (i < 6 && j < 6)
                            if (squares[i + 1][j + 1].getColor() != 0 && squares[i + 1][j + 1].getColor() != squares[i][j].getColor() && squares[i + 2][j + 2].getColor() == 0)
                                mf = 1;
                        if (i > 1 && j < 6)
                            if (squares[i - 1][j + 1].getColor() != 0 && squares[i - 1][j + 1].getColor() != squares[i][j].getColor() && squares[i - 2][j + 2].getColor() == 0)
                                mf = 1;
                        if (i > 1 && j > 1)
                            if (squares[i - 1][j - 1].getColor() != 0 && squares[i - 1][j - 1].getColor() != squares[i][j].getColor() && squares[i - 2][j - 2].getColor() == 0)
                                mf = 1;
                        if (i < 6 && j > 1)
                            if (squares[i + 1][j - 1].getColor() != 0 && squares[i + 1][j - 1].getColor() != squares[i][j].getColor() && squares[i + 2][j - 2].getColor() == 0)
                                mf = 1;
                    } else {
                        r = squares[i][j].getColor();
                        m = squares[i][j].getV() - 1;
                        n = squares[i][j].getH() - 1;
                        while (m < 6 && n < 6) {
                            if (squares[m + 1][n + 1].getColor() == r || squares[m + 1][n + 1].getColor() != 0 && squares[m + 2][n + 2].getColor() != 0)
                                break;
                            if (squares[m + 1][n + 1].getColor() != r && squares[m + 1][n + 1].getColor() != 0) {
                                l++;
                                if (l >= 2) break;
                            }
                            if (squares[m + 1][n + 1].getColor() != r && squares[m + 1][n + 1].getColor() != 0 && squares[m + 2][n + 2].getColor() == 0) {
                                mf = 1;
                                break;
                            }
                            m++;
                            n++;
                        }
                        l = 0;
                        m = squares[i][j].getV() - 1;
                        n = squares[i][j].getH() - 1;
                        while (m > 1 && n < 6 && fa == 0) {
                            if (squares[m - 1][n + 1].getColor() == r || squares[m - 1][n + 1].getColor() != 0 && squares[m - 2][n + 2].getColor() != 0)
                                break;
                            if (squares[m - 1][n + 1].getColor() != r && squares[m - 1][n + 1].getColor() != 0) {
                                l++;
                                if (l >= 2) break;
                            }
                            if (squares[m - 1][n + 1].getColor() != r && squares[m - 1][n + 1].getColor() != 0 && squares[m - 2][n + 2].getColor() == 0) {
                                mf = 1;
                                break;
                            }
                            m--;
                            n++;
                        }
                        l = 0;
                        m = squares[i][j].getV() - 1;
                        n = squares[i][j].getH() - 1;
                        while (m > 1 && n > 1 && fa == 0) {
                            if (squares[m - 1][n - 1].getColor() == r || squares[m - 1][n - 1].getColor() != 0 && squares[m - 2][n - 2].getColor() != 0)
                                break;
                            if (squares[m - 1][n - 1].getColor() != r && squares[m - 1][n - 1].getColor() != 0) {
                                l++;
                                if (l >= 2) break;
                            }
                            if (squares[m - 1][n - 1].getColor() != r && squares[m - 1][n - 1].getColor() != 0 && squares[m - 2][n - 2].getColor() == 0) {
                                mf = 1;
                                break;
                            }
                            m--;
                            n--;
                        }
                        l = 0;
                        m = squares[i][j].getV() - 1;
                        n = squares[i][j].getH() - 1;
                        while (m < 6 && n > 1 && fa == 0) {
                            if (squares[m + 1][n - 1].getColor() == r || squares[m + 1][n - 1].getColor() != 0 && squares[m + 2][n - 2].getColor() != 0)
                                break;
                            if (squares[m + 1][n - 1].getColor() != r && squares[m + 1][n - 1].getColor() != 0) {
                                l++;
                                if (l >= 2) break;
                            }
                            if (squares[m + 1][n - 1].getColor() != r && squares[m + 1][n - 1].getColor() != 0 && squares[m + 2][n - 2].getColor() == 0) {
                                mf = 1;
                                break;
                            }
                            m++;
                            n--;
                        }
                        l = 0;
                    }
                }
            }
        }
    }

    public void fight(Canvas canvas, Square s1, Square s2, Square s3, Bitmap b) {
        drawSquare(canvas, s1, b);
        drawSquare(canvas, s2, bs);
        drawSquare(canvas, s3, bs);
        if (s2.isQueen()) s2.setQueen(false);
        if (!s1.isQueen()) {
            r = s1.getColor();
            m = s1.getV() - 1;
            n = s1.getH() - 1;
            if (m < 6 && n < 6)
                if (squares[m + 1][n + 1].getColor() != 0 && squares[m + 1][n + 1].getColor() != squares[m][n].getColor() && squares[m + 2][n + 2].getColor() == 0)
                    fa = 1;
            if (m > 1 && n < 6)
                if (squares[m - 1][n + 1].getColor() != 0 && squares[m - 1][n + 1].getColor() != squares[m][n].getColor() && squares[m - 2][n + 2].getColor() == 0)
                    fa = 1;
            if (m > 1 && n > 1)
                if (squares[m - 1][n - 1].getColor() != 0 && squares[m - 1][n - 1].getColor() != squares[m][n].getColor() && squares[m - 2][n - 2].getColor() == 0)
                    fa = 1;
            if (m < 6 && n > 1)
                if (squares[m + 1][n - 1].getColor() != 0 && squares[m + 1][n - 1].getColor() != squares[m][n].getColor() && squares[m + 2][n - 2].getColor() == 0)
                    fa = 1;
        } else {
            r = s1.getColor();
            m = s1.getV() - 1;
            n = s1.getH() - 1;
            while (m < 6 && n < 6) {
                if (squares[m + 1][n + 1].getColor() == r || squares[m + 1][n + 1].getColor() != 0 && squares[m + 2][n + 2].getColor() != 0)
                    break;
                if (squares[m + 1][n + 1].getColor() != r && squares[m + 1][n + 1].getColor() != 0) {
                    l++;
                    if (l >= 2) break;
                }
                if (squares[m + 1][n + 1].getColor() != r && squares[m + 1][n + 1].getColor() != 0 && squares[m + 2][n + 2].getColor() == 0) {
                    fa = 1;
                    break;
                }
                m++;
                n++;
            }
            l = 0;
            m = s1.getV() - 1;
            n = s1.getH() - 1;
            while (m > 1 && n < 6 && fa == 0) {
                if (squares[m - 1][n + 1].getColor() == r || squares[m - 1][n + 1].getColor() != 0 && squares[m - 2][n + 2].getColor() != 0)
                    break;
                if (squares[m - 1][n + 1].getColor() != r && squares[m - 1][n + 1].getColor() != 0) {
                    l++;
                    if (l >= 2) break;
                }
                if (squares[m - 1][n + 1].getColor() != r && squares[m - 1][n + 1].getColor() != 0 && squares[m - 2][n + 2].getColor() == 0) {
                    fa = 1;
                    break;
                }
                m--;
                n++;
            }
            l = 0;
            m = s1.getV() - 1;
            n = s1.getH() - 1;
            while (m > 1 && n > 1 && fa == 0) {
                if (squares[m - 1][n - 1].getColor() == r || squares[m - 1][n - 1].getColor() != 0 && squares[m - 2][n - 2].getColor() != 0)
                    break;
                if (squares[m - 1][n - 1].getColor() != r && squares[m - 1][n - 1].getColor() != 0) {
                    l++;
                    if (l >= 2) break;
                }
                if (squares[m - 1][n - 1].getColor() != r && squares[m - 1][n - 1].getColor() != 0 && squares[m - 2][n - 2].getColor() == 0) {
                    fa = 1;
                    break;
                }
                m--;
                n--;
            }
            l = 0;
            m = s1.getV() - 1;
            n = s1.getH() - 1;
            while (m < 6 && n > 1 && fa == 0) {
                if (squares[m + 1][n - 1].getColor() == r || squares[m + 1][n - 1].getColor() != 0 && squares[m + 2][n - 2].getColor() != 0)
                    break;
                if (squares[m + 1][n - 1].getColor() != r && squares[m + 1][n - 1].getColor() != 0) {
                    l++;
                    if (l >= 2) break;
                }
                if (squares[m + 1][n - 1].getColor() != r && squares[m + 1][n - 1].getColor() != 0 && squares[m + 2][n - 2].getColor() == 0) {
                    fa = 1;
                    break;
                }
                m++;
                n--;
            }
            l = 0;
        }
        if (who == 1 && fa == 0) who = 2;
        else if (who == 2 && fa == 0) who = 1;
        mf = 0;
        m = 0;
        n = 0;
        for (int i = 0; i < squares.length; i++) {
            for (int j = 0; j < squares[i].length; j++) {
                if (squares[i][j].getColor() == who) {
                    if (!squares[i][j].isQueen()) {
                        r = squares[i][j].getColor();
                        if (i < 6 && j < 6)
                            if (squares[i + 1][j + 1].getColor() != 0 && squares[i + 1][j + 1].getColor() != squares[i][j].getColor() && squares[i + 2][j + 2].getColor() == 0)
                                mf = 1;
                        if (i > 1 && j < 6)
                            if (squares[i - 1][j + 1].getColor() != 0 && squares[i - 1][j + 1].getColor() != squares[i][j].getColor() && squares[i - 2][j + 2].getColor() == 0)
                                mf = 1;
                        if (i > 1 && j > 1)
                            if (squares[i - 1][j - 1].getColor() != 0 && squares[i - 1][j - 1].getColor() != squares[i][j].getColor() && squares[i - 2][j - 2].getColor() == 0)
                                mf = 1;
                        if (i < 6 && j > 1)
                            if (squares[i + 1][j - 1].getColor() != 0 && squares[i + 1][j - 1].getColor() != squares[i][j].getColor() && squares[i + 2][j - 2].getColor() == 0)
                                mf = 1;
                    } else {
                        r = squares[i][j].getColor();
                        m = squares[i][j].getV() - 1;
                        n = squares[i][j].getH() - 1;
                        while (m < 6 && n < 6) {
                            if (squares[m + 1][n + 1].getColor() == r || squares[m + 1][n + 1].getColor() != 0 && squares[m + 2][n + 2].getColor() != 0)
                                break;
                            if (squares[m + 1][n + 1].getColor() != r && squares[m + 1][n + 1].getColor() != 0) {
                                l++;
                                if (l >= 2) break;
                            }
                            if (squares[m + 1][n + 1].getColor() != r && squares[m + 1][n + 1].getColor() != 0 && squares[m + 2][n + 2].getColor() == 0) {
                                mf = 1;
                                break;
                            }
                            m++;
                            n++;
                        }
                        l = 0;
                        m = squares[i][j].getV() - 1;
                        n = squares[i][j].getH() - 1;
                        while (m > 1 && n < 6 && fa == 0) {
                            if (squares[m - 1][n + 1].getColor() == r || squares[m - 1][n + 1].getColor() != 0 && squares[m - 2][n + 2].getColor() != 0)
                                break;
                            if (squares[m - 1][n + 1].getColor() != r && squares[m - 1][n + 1].getColor() != 0) {
                                l++;
                                if (l >= 2) break;
                            }
                            if (squares[m - 1][n + 1].getColor() != r && squares[m - 1][n + 1].getColor() != 0 && squares[m - 2][n + 2].getColor() == 0) {
                                mf = 1;
                                break;
                            }
                            m--;
                            n++;
                        }
                        l = 0;
                        m = squares[i][j].getV() - 1;
                        n = squares[i][j].getH() - 1;
                        while (m > 1 && n > 1 && fa == 0) {
                            if (squares[m - 1][n - 1].getColor() == r || squares[m - 1][n - 1].getColor() != 0 && squares[m - 2][n - 2].getColor() != 0)
                                break;
                            if (squares[m - 1][n - 1].getColor() != r && squares[m - 1][n - 1].getColor() != 0) {
                                l++;
                                if (l >= 2) break;
                            }
                            if (squares[m - 1][n - 1].getColor() != r && squares[m - 1][n - 1].getColor() != 0 && squares[m - 2][n - 2].getColor() == 0) {
                                mf = 1;
                                break;
                            }
                            m--;
                            n--;
                        }
                        l = 0;
                        m = squares[i][j].getV() - 1;
                        n = squares[i][j].getH() - 1;
                        while (m < 6 && n > 1 && fa == 0) {
                            if (squares[m + 1][n - 1].getColor() == r || squares[m + 1][n - 1].getColor() != 0 && squares[m + 2][n - 2].getColor() != 0)
                                break;
                            if (squares[m + 1][n - 1].getColor() != r && squares[m + 1][n - 1].getColor() != 0) {
                                l++;
                                if (l >= 2) break;
                            }
                            if (squares[m + 1][n - 1].getColor() != r && squares[m + 1][n - 1].getColor() != 0 && squares[m + 2][n - 2].getColor() == 0) {
                                mf = 1;
                                break;
                            }
                            m++;
                            n--;
                        }
                        l = 0;
                    }
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        touchX = event.getX();
        touchY = event.getY();
        if (event.getAction() == MotionEvent.ACTION_UP) {
            for (int i = 0; i < squares.length; i++) {
                for (int j = 0; j < squares[i].length; j++) {
                    if (touchX > (squares[i][j].getV() - 1) * getWidth() / 8 &&
                            touchX < squares[i][j].getV() * getWidth() / 8 &&
                            touchY > getHeight() / 2 - getWidth() / 2 + ((8 - squares[i][j].getH()) * getWidth() / 8) &&
                            touchY < getHeight() / 2 - getWidth() / 2 + ((9 - squares[i][j].getH()) * getWidth() / 8) &&
                            i % 2 == j % 2) {
                        switch (squares[i][j].getColor()) {
                            case 0:
                                for (Square[] square : squares) {
                                    for (int l = 0; l < squares[i].length; l++) {
                                        if (square[l].isD() && square[l].getColor() != 0) {
                                            check3 = square[l].getColor();
                                            if (fa == 0) t = 0;
                                        } else t = 1;
                                    }
                                }
                                if (fa == 1) {
                                    check1 = 1;
                                    fa = 0;
                                }
                                if (who == check3) {
                                    for (int k = 0; k < squares.length; k++) {
                                        for (int l = 0; l < squares[i].length; l++) {
                                            if (squares[k][l].isD() && squares[k][l].isQueen()) {
                                                if (i > k && j > l && i - k == j - l) {
                                                    squares[k][l].setMove(1);
                                                    int m = k;
                                                    int n = l;
                                                    while (k < i && l < j) {
                                                        k++;
                                                        l++;
                                                        if (squares[k][l].getColor() == squares[m][n].getColor()) {
                                                            flag++;
                                                            break;
                                                        } else if (squares[k][l].getColor() != 0) {
                                                            check2++;
                                                            o = k;
                                                            q = l;
                                                            if (check2 > 1) {
                                                                flag++;
                                                                break;
                                                            }
                                                        }
                                                    }
                                                    if (flag == 0) {
                                                        if (check2 == 1) {
                                                            fight(new Canvas(), squares[i][j], squares[o][q], squares[m][n], squares[m][n].getB());
                                                            squares[i][j].setQueen(true);
                                                            squares[m][n].setQueen(false);
                                                        } else if (check2 == 0 && mf == 0) {
                                                            move(new Canvas(), squares[i][j], squares[m][n], squares[m][n].getB());
                                                            squares[i][j].setQueen(true);
                                                            squares[m][n].setQueen(false);
                                                        }
                                                    }
                                                    check2 = 0;
                                                    flag = 0;
                                                    o = 0;
                                                    q = 0;
                                                    squares[m][n].setMove(0);
                                                } else if (i < k && j > l && k - i == j - l) {
                                                    squares[k][l].setMove(2);
                                                    int m = k;
                                                    int n = l;
                                                    while (i < k && l < j) {
                                                        k--;
                                                        l++;
                                                        if (squares[k][l].getColor() == squares[m][n].getColor()) {
                                                            flag++;
                                                            break;
                                                        } else if (squares[k][l].getColor() != 0) {
                                                            check2++;
                                                            o = k;
                                                            q = l;
                                                            if (check2 > 1) {
                                                                flag++;
                                                                break;
                                                            }
                                                        }
                                                    }
                                                    if (flag == 0) {
                                                        if (check2 == 1) {
                                                            fight(new Canvas(), squares[i][j], squares[o][q], squares[m][n], squares[m][n].getB());
                                                            squares[i][j].setQueen(true);
                                                            squares[m][n].setQueen(false);
                                                        } else if (check2 == 0 && mf == 0) {
                                                            move(new Canvas(), squares[i][j], squares[m][n], squares[m][n].getB());
                                                            squares[i][j].setQueen(true);
                                                            squares[m][n].setQueen(false);
                                                        }
                                                    }
                                                    check2 = 0;
                                                    flag = 0;
                                                    o = 0;
                                                    q = 0;
                                                    squares[m][n].setMove(0);
                                                } else if (i < k && j < l && k - i == l - j) {
                                                    squares[k][l].setMove(3);
                                                    int m = k;
                                                    int n = l;
                                                    while (i < k && j < l) {
                                                        k--;
                                                        l--;
                                                        if (squares[k][l].getColor() == squares[m][n].getColor()) {
                                                            flag++;
                                                            break;
                                                        } else if (squares[k][l].getColor() != 0) {
                                                            check2++;
                                                            o = k;
                                                            q = l;
                                                            if (check2 > 1) {
                                                                flag++;
                                                                break;
                                                            }
                                                        }
                                                    }
                                                    if (flag == 0) {
                                                        if (check2 == 1) {
                                                            fight(new Canvas(), squares[i][j], squares[o][q], squares[m][n], squares[m][n].getB());
                                                            squares[i][j].setQueen(true);
                                                            squares[m][n].setQueen(false);
                                                        } else if (check2 == 0 && mf == 0) {
                                                            move(new Canvas(), squares[i][j], squares[m][n], squares[m][n].getB());
                                                            squares[i][j].setQueen(true);
                                                            squares[m][n].setQueen(false);
                                                        }
                                                    }
                                                    check2 = 0;
                                                    flag = 0;
                                                    o = 0;
                                                    q = 0;
                                                    squares[m][n].setMove(0);
                                                } else if (i > k && j < l && i - k == l - j) {
                                                    squares[k][l].setMove(4);
                                                    int m = k;
                                                    int n = l;
                                                    while (k < i && j < l) {
                                                        k++;
                                                        l--;
                                                        if (squares[k][l].getColor() == squares[m][n].getColor()) {
                                                            flag++;
                                                            break;
                                                        } else if (squares[k][l].getColor() != 0) {
                                                            check2++;
                                                            o = k;
                                                            q = l;
                                                            if (check2 > 1) {
                                                                flag++;
                                                                break;
                                                            }
                                                        }
                                                    }
                                                    if (flag == 0) {
                                                        if (check2 == 1) {
                                                            fight(new Canvas(), squares[i][j], squares[o][q], squares[m][n], squares[m][n].getB());
                                                            squares[i][j].setQueen(true);
                                                            squares[m][n].setQueen(false);
                                                        } else if (check2 == 0 && mf == 0) {
                                                            move(new Canvas(), squares[i][j], squares[m][n], squares[m][n].getB());
                                                            squares[i][j].setQueen(true);
                                                            squares[m][n].setQueen(false);
                                                        }
                                                    }
                                                    check2 = 0;
                                                    flag = 0;
                                                    o = 0;
                                                    q = 0;
                                                    squares[m][n].setMove(0);
                                                }
                                            }
                                        }
                                    }
                                    switch (i) {
                                        case 2:
                                        case 3:
                                        case 4:
                                        case 5:
                                            switch (j) {
                                                case 2:
                                                case 3:
                                                case 4:
                                                case 5:
                                                    if (squares[i - 1][j - 1].isD() && squares[i - 1][j - 1].getColor() == 1 && mf == 0) {
                                                        move(new Canvas(), squares[i][j], squares[i - 1][j - 1], wc);
                                                    } else if (squares[i + 1][j - 1].isD() && squares[i + 1][j - 1].getColor() == 1 && mf == 0) {
                                                        move(new Canvas(), squares[i][j], squares[i + 1][j - 1], wc);
                                                    } else if (squares[i - 1][j + 1].isD() && squares[i - 1][j + 1].getColor() == 2 && mf == 0) {
                                                        move(new Canvas(), squares[i][j], squares[i - 1][j + 1], bc);
                                                    } else if (squares[i + 1][j + 1].isD() && squares[i + 1][j + 1].getColor() == 2 && mf == 0) {
                                                        move(new Canvas(), squares[i][j], squares[i + 1][j + 1], bc);
                                                    } else if (squares[i - 2][j - 2].isD()) {
                                                        if (squares[i - 1][j - 1].getColor() == 1 && squares[i - 2][j - 2].getColor() == 2) {
                                                            fight(new Canvas(), squares[i][j], squares[i - 1][j - 1], squares[i - 2][j - 2], bc);
                                                        } else if (squares[i - 1][j - 1].getColor() == 2 && squares[i - 2][j - 2].getColor() == 1) {
                                                            fight(new Canvas(), squares[i][j], squares[i - 1][j - 1], squares[i - 2][j - 2], wc);
                                                        }
                                                    } else if (squares[i + 2][j - 2].isD()) {
                                                        if (squares[i + 1][j - 1].getColor() == 1 && squares[i + 2][j - 2].getColor() == 2) {
                                                            fight(new Canvas(), squares[i][j], squares[i + 1][j - 1], squares[i + 2][j - 2], bc);
                                                        } else if (squares[i + 1][j - 1].getColor() == 2 && squares[i + 2][j - 2].getColor() == 1) {
                                                            fight(new Canvas(), squares[i][j], squares[i + 1][j - 1], squares[i + 2][j - 2], wc);
                                                        }
                                                    } else if (squares[i - 2][j + 2].isD()) {
                                                        if (squares[i - 1][j + 1].getColor() == 1 && squares[i - 2][j + 2].getColor() == 2) {
                                                            fight(new Canvas(), squares[i][j], squares[i - 1][j + 1], squares[i - 2][j + 2], bc);
                                                        } else if (squares[i - 1][j + 1].getColor() == 2 && squares[i - 2][j + 2].getColor() == 1) {
                                                            fight(new Canvas(), squares[i][j], squares[i - 1][j + 1], squares[i - 2][j + 2], wc);
                                                        }
                                                    } else if (squares[i + 2][j + 2].isD()) {
                                                        if (squares[i + 1][j + 1].getColor() == 1 && squares[i + 2][j + 2].getColor() == 2) {
                                                            fight(new Canvas(), squares[i][j], squares[i + 1][j + 1], squares[i + 2][j + 2], bc);
                                                        } else if (squares[i + 1][j + 1].getColor() == 2 && squares[i + 2][j + 2].getColor() == 1) {
                                                            fight(new Canvas(), squares[i][j], squares[i + 1][j + 1], squares[i + 2][j + 2], wc);
                                                        }
                                                    } else {
                                                        for (Square[] square : squares) {
                                                            for (int l = 0; l < squares[i].length; l++) {
                                                                if (square[l].isD())
                                                                    square[l].setD(false);
                                                            }
                                                        }
                                                    }
                                                    break;
                                                case 0:
                                                    if (squares[i - 1][j + 1].isD() && squares[i - 1][j + 1].getColor() == 2 && mf == 0) {
                                                        move(new Canvas(), squares[i][j], squares[i - 1][j + 1], bc);
                                                        squares[i][j].setQueen(true);
                                                    } else if (squares[i + 1][j + 1].isD() && squares[i + 1][j + 1].getColor() == 2 && mf == 0) {
                                                        move(new Canvas(), squares[i][j], squares[i + 1][j + 1], bc);
                                                        squares[i][j].setQueen(true);
                                                    } else if (squares[i - 2][j + 2].isD()) {
                                                        if (squares[i - 1][j + 1].getColor() == 1 && squares[i - 2][j + 2].getColor() == 2) {
                                                            fight(new Canvas(), squares[i][j], squares[i - 1][j + 1], squares[i - 2][j + 2], bc);
                                                            squares[i][j].setQueen(true);
                                                        } else if (squares[i - 1][j + 1].getColor() == 2 && squares[i - 2][j + 2].getColor() == 1) {
                                                            fight(new Canvas(), squares[i][j], squares[i - 1][j + 1], squares[i - 2][j + 2], wc);
                                                            squares[i][j].setMove(4);
                                                        }
                                                    } else if (squares[i + 2][j + 2].isD()) {
                                                        if (squares[i + 1][j + 1].getColor() == 1 && squares[i + 2][j + 2].getColor() == 2) {
                                                            fight(new Canvas(), squares[i][j], squares[i + 1][j + 1], squares[i + 2][j + 2], bc);
                                                            squares[i][j].setQueen(true);
                                                        } else if (squares[i + 1][j + 1].getColor() == 2 && squares[i + 2][j + 2].getColor() == 1) {
                                                            fight(new Canvas(), squares[i][j], squares[i + 1][j + 1], squares[i + 2][j + 2], wc);
                                                        }
                                                    } else {
                                                        for (Square[] square : squares) {
                                                            for (int l = 0; l < squares[i].length; l++) {
                                                                if (square[l].isD())
                                                                    square[l].setD(false);
                                                            }
                                                        }
                                                    }
                                                    break;
                                                case 1:
                                                    if (squares[i - 1][j - 1].isD() && squares[i - 1][j - 1].getColor() == 1 && mf == 0) {
                                                        move(new Canvas(), squares[i][j], squares[i - 1][j - 1], wc);
                                                    } else if (squares[i + 1][j - 1].isD() && squares[i + 1][j - 1].getColor() == 1 && mf == 0) {
                                                        move(new Canvas(), squares[i][j], squares[i + 1][j - 1], wc);
                                                    } else if (squares[i - 1][j + 1].isD() && squares[i - 1][j + 1].getColor() == 2 && mf == 0) {
                                                        move(new Canvas(), squares[i][j], squares[i - 1][j + 1], bc);
                                                    } else if (squares[i + 1][j + 1].isD() && squares[i + 1][j + 1].getColor() == 2 && mf == 0) {
                                                        move(new Canvas(), squares[i][j], squares[i + 1][j + 1], bc);
                                                    } else if (squares[i - 2][j + 2].isD()) {
                                                        if (squares[i - 1][j + 1].getColor() == 1 && squares[i - 2][j + 2].getColor() == 2) {
                                                            fight(new Canvas(), squares[i][j], squares[i - 1][j + 1], squares[i - 2][j + 2], bc);
                                                        } else if (squares[i - 1][j + 1].getColor() == 2 && squares[i - 2][j + 2].getColor() == 1) {
                                                            fight(new Canvas(), squares[i][j], squares[i - 1][j + 1], squares[i - 2][j + 2], wc);
                                                        }
                                                    } else if (squares[i + 2][j + 2].isD()) {
                                                        if (squares[i + 1][j + 1].getColor() == 1 && squares[i + 2][j + 2].getColor() == 2) {
                                                            fight(new Canvas(), squares[i][j], squares[i + 1][j + 1], squares[i + 2][j + 2], bc);
                                                        } else if (squares[i + 1][j + 1].getColor() == 2 && squares[i + 2][j + 2].getColor() == 1) {
                                                            fight(new Canvas(), squares[i][j], squares[i + 1][j + 1], squares[i + 2][j + 2], wc);
                                                        }
                                                    } else {
                                                        for (Square[] square : squares) {
                                                            for (int l = 0; l < squares[i].length; l++) {
                                                                if (square[l].isD())
                                                                    square[l].setD(false);
                                                            }
                                                        }
                                                    }
                                                    break;
                                                case 6:
                                                    if (squares[i - 1][j - 1].isD() && squares[i - 1][j - 1].getColor() == 1 && mf == 0) {
                                                        move(new Canvas(), squares[i][j], squares[i - 1][j - 1], wc);
                                                    } else if (squares[i + 1][j - 1].isD() && squares[i + 1][j - 1].getColor() == 1 && mf == 0) {
                                                        move(new Canvas(), squares[i][j], squares[i + 1][j - 1], wc);
                                                    } else if (squares[i - 1][j + 1].isD() && squares[i - 1][j + 1].getColor() == 2 && mf == 0) {
                                                        move(new Canvas(), squares[i][j], squares[i - 1][j + 1], bc);
                                                    } else if (squares[i + 1][j + 1].isD() && squares[i + 1][j + 1].getColor() == 2 && mf == 0) {
                                                        move(new Canvas(), squares[i][j], squares[i + 1][j + 1], bc);
                                                    } else if (squares[i - 2][j - 2].isD()) {
                                                        if (squares[i - 1][j - 1].getColor() == 1 && squares[i - 2][j - 2].getColor() == 2) {
                                                            fight(new Canvas(), squares[i][j], squares[i - 1][j - 1], squares[i - 2][j - 2], bc);
                                                        } else if (squares[i - 1][j - 1].getColor() == 2 && squares[i - 2][j - 2].getColor() == 1) {
                                                            fight(new Canvas(), squares[i][j], squares[i - 1][j - 1], squares[i - 2][j - 2], wc);
                                                        }
                                                    } else if (squares[i + 2][j - 2].isD()) {
                                                        if (squares[i + 1][j - 1].getColor() == 1 && squares[i + 2][j - 2].getColor() == 2) {
                                                            fight(new Canvas(), squares[i][j], squares[i + 1][j - 1], squares[i + 2][j - 2], bc);
                                                        } else if (squares[i + 1][j - 1].getColor() == 2 && squares[i + 2][j - 2].getColor() == 1) {
                                                            fight(new Canvas(), squares[i][j], squares[i + 1][j - 1], squares[i + 2][j - 2], wc);
                                                        }
                                                    } else {
                                                        for (Square[] square : squares) {
                                                            for (int l = 0; l < squares[i].length; l++) {
                                                                if (square[l].isD())
                                                                    square[l].setD(false);
                                                            }
                                                        }
                                                    }
                                                    break;
                                                case 7:
                                                    if (squares[i - 1][j - 1].isD() && squares[i - 1][j - 1].getColor() == 1 && mf == 0) {
                                                        move(new Canvas(), squares[i][j], squares[i - 1][j - 1], wc);
                                                        squares[i][j].setQueen(true);
                                                    } else if (squares[i + 1][j - 1].isD() && squares[i + 1][j - 1].getColor() == 1 && mf == 0) {
                                                        move(new Canvas(), squares[i][j], squares[i + 1][j - 1], wc);
                                                        squares[i][j].setQueen(true);
                                                    } else if (squares[i - 2][j - 2].isD()) {
                                                        if (squares[i - 1][j - 1].getColor() == 1 && squares[i - 2][j - 2].getColor() == 2) {
                                                            fight(new Canvas(), squares[i][j], squares[i - 1][j - 1], squares[i - 2][j - 2], bc);
                                                        } else if (squares[i - 1][j - 1].getColor() == 2 && squares[i - 2][j - 2].getColor() == 1) {
                                                            fight(new Canvas(), squares[i][j], squares[i - 1][j - 1], squares[i - 2][j - 2], wc);
                                                            squares[i][j].setQueen(true);
                                                        }
                                                    } else if (squares[i + 2][j - 2].isD()) {
                                                        if (squares[i + 1][j - 1].getColor() == 1 && squares[i + 2][j - 2].getColor() == 2) {
                                                            fight(new Canvas(), squares[i][j], squares[i + 1][j - 1], squares[i + 2][j - 2], bc);
                                                        } else if (squares[i + 1][j - 1].getColor() == 2 && squares[i + 2][j - 2].getColor() == 1) {
                                                            fight(new Canvas(), squares[i][j], squares[i + 1][j - 1], squares[i + 2][j - 2], wc);
                                                            squares[i][j].setQueen(true);
                                                        }
                                                    } else {
                                                        for (Square[] square : squares) {
                                                            for (int l = 0; l < squares[i].length; l++) {
                                                                if (square[l].isD())
                                                                    square[l].setD(false);
                                                            }
                                                        }
                                                    }
                                                    break;
                                            }
                                            break;
                                        case 1:
                                            switch (j) {
                                                case 3:
                                                case 5:
                                                    if (squares[i - 1][j - 1].isD() && squares[i - 1][j - 1].getColor() == 1 && mf == 0) {
                                                        move(new Canvas(), squares[i][j], squares[i - 1][j - 1], wc);
                                                    } else if (squares[i + 1][j - 1].isD() && squares[i + 1][j - 1].getColor() == 1 && mf == 0) {
                                                        move(new Canvas(), squares[i][j], squares[i + 1][j - 1], wc);
                                                    } else if (squares[i - 1][j + 1].isD() && squares[i - 1][j + 1].getColor() == 2 && mf == 0) {
                                                        move(new Canvas(), squares[i][j], squares[i - 1][j + 1], bc);
                                                    } else if (squares[i + 1][j + 1].isD() && squares[i + 1][j + 1].getColor() == 2 && mf == 0) {
                                                        move(new Canvas(), squares[i][j], squares[i + 1][j + 1], bc);
                                                    } else if (squares[i + 2][j - 2].isD()) {
                                                        if (squares[i + 1][j - 1].getColor() == 1 && squares[i + 2][j - 2].getColor() == 2)
                                                            fight(new Canvas(), squares[i][j], squares[i + 1][j - 1], squares[i + 2][j - 2], bc);
                                                        else if (squares[i + 1][j - 1].getColor() == 2 && squares[i + 2][j - 2].getColor() == 1)
                                                            fight(new Canvas(), squares[i][j], squares[i + 1][j - 1], squares[i + 2][j - 2], wc);
                                                    } else if (squares[i + 2][j + 2].isD()) {
                                                        if (squares[i + 1][j + 1].getColor() == 1 && squares[i + 2][j + 2].getColor() == 2)
                                                            fight(new Canvas(), squares[i][j], squares[i + 1][j + 1], squares[i + 2][j + 2], bc);
                                                        else if (squares[i + 1][j + 1].getColor() == 2 && squares[i + 2][j + 2].getColor() == 1)
                                                            fight(new Canvas(), squares[i][j], squares[i + 1][j + 1], squares[i + 2][j + 2], wc);
                                                    } else {
                                                        for (Square[] square : squares) {
                                                            for (int l = 0; l < squares[i].length; l++) {
                                                                if (square[l].isD())
                                                                    square[l].setD(false);
                                                            }
                                                        }
                                                    }
                                                    break;
                                                case 1:
                                                    if (squares[i - 1][j - 1].isD() && squares[i - 1][j - 1].getColor() == 1 && mf == 0) {
                                                        move(new Canvas(), squares[i][j], squares[i - 1][j - 1], wc);
                                                    } else if (squares[i + 1][j - 1].isD() && squares[i + 1][j - 1].getColor() == 1 && mf == 0) {
                                                        move(new Canvas(), squares[i][j], squares[i + 1][j - 1], wc);
                                                    } else if (squares[i - 1][j + 1].isD() && squares[i - 1][j + 1].getColor() == 2 && mf == 0) {
                                                        move(new Canvas(), squares[i][j], squares[i - 1][j + 1], bc);
                                                    } else if (squares[i + 1][j + 1].isD() && squares[i + 1][j + 1].getColor() == 2 && mf == 0) {
                                                        move(new Canvas(), squares[i][j], squares[i + 1][j + 1], bc);
                                                    } else if (squares[i + 2][j + 2].isD()) {
                                                        if (squares[i + 1][j + 1].getColor() == 1 && squares[i + 2][j + 2].getColor() == 2)
                                                            fight(new Canvas(), squares[i][j], squares[i + 1][j + 1], squares[i + 2][j + 2], bc);
                                                        else if (squares[i + 1][j + 1].getColor() == 2 && squares[i + 2][j + 2].getColor() == 1)
                                                            fight(new Canvas(), squares[i][j], squares[i + 1][j + 1], squares[i + 2][j + 2], wc);
                                                    } else {
                                                        for (Square[] square : squares) {
                                                            for (int l = 0; l < squares[i].length; l++) {
                                                                if (square[l].isD())
                                                                    square[l].setD(false);
                                                            }
                                                        }
                                                    }
                                                    break;
                                                case 7:
                                                    if (squares[i - 1][j - 1].isD() && squares[i - 1][j - 1].getColor() == 1 && mf == 0) {
                                                        move(new Canvas(), squares[i][j], squares[i - 1][j - 1], wc);
                                                        squares[i][j].setQueen(true);
                                                    } else if (squares[i + 1][j - 1].isD() && squares[i + 1][j - 1].getColor() == 1 && mf == 0) {
                                                        move(new Canvas(), squares[i][j], squares[i + 1][j - 1], wc);
                                                        squares[i][j].setQueen(true);
                                                    } else if (squares[i + 2][j - 2].isD()) {
                                                        if (squares[i + 1][j - 1].getColor() == 1 && squares[i + 2][j - 2].getColor() == 2)
                                                            fight(new Canvas(), squares[i][j], squares[i + 1][j - 1], squares[i + 2][j - 2], bc);
                                                        else if (squares[i + 1][j - 1].getColor() == 2 && squares[i + 2][j - 2].getColor() == 1) {
                                                            fight(new Canvas(), squares[i][j], squares[i + 1][j - 1], squares[i + 2][j - 2], wc);
                                                            squares[i][j].setQueen(true);
                                                        }
                                                    } else {
                                                        for (Square[] square : squares) {
                                                            for (int l = 0; l < squares[i].length; l++) {
                                                                if (square[l].isD())
                                                                    square[l].setD(false);
                                                            }
                                                        }
                                                    }
                                                    break;
                                            }
                                            break;
                                        case 6:
                                            switch (j) {
                                                case 2:
                                                case 4:
                                                    if (squares[i - 1][j - 1].isD() && squares[i - 1][j - 1].getColor() == 1 && mf == 0) {
                                                        move(new Canvas(), squares[i][j], squares[i - 1][j - 1], wc);
                                                    } else if (squares[i + 1][j - 1].isD() && squares[i + 1][j - 1].getColor() == 1 && mf == 0) {
                                                        move(new Canvas(), squares[i][j], squares[i + 1][j - 1], wc);
                                                    } else if (squares[i - 1][j + 1].isD() && squares[i - 1][j + 1].getColor() == 2 && mf == 0) {
                                                        move(new Canvas(), squares[i][j], squares[i - 1][j + 1], bc);
                                                    } else if (squares[i + 1][j + 1].isD() && squares[i + 1][j + 1].getColor() == 2 && mf == 0) {
                                                        move(new Canvas(), squares[i][j], squares[i + 1][j + 1], bc);
                                                    } else if (squares[i - 2][j - 2].isD()) {
                                                        if (squares[i - 1][j - 1].getColor() == 1 && squares[i - 2][j - 2].getColor() == 2)
                                                            fight(new Canvas(), squares[i][j], squares[i - 1][j - 1], squares[i - 2][j - 2], bc);
                                                        else if (squares[i - 1][j - 1].getColor() == 2 && squares[i - 2][j - 2].getColor() == 1)
                                                            fight(new Canvas(), squares[i][j], squares[i - 1][j - 1], squares[i - 2][j - 2], wc);
                                                    } else if (squares[i - 2][j + 2].isD()) {
                                                        if (squares[i - 1][j + 1].getColor() == 1 && squares[i - 2][j + 2].getColor() == 2)
                                                            fight(new Canvas(), squares[i][j], squares[i - 1][j + 1], squares[i - 2][j + 2], bc);
                                                        else if (squares[i - 1][j + 1].getColor() == 2 && squares[i - 2][j + 2].getColor() == 1)
                                                            fight(new Canvas(), squares[i][j], squares[i - 1][j + 1], squares[i - 2][j + 2], wc);
                                                    } else {
                                                        for (Square[] square : squares) {
                                                            for (int l = 0; l < squares[i].length; l++) {
                                                                if (square[l].isD())
                                                                    square[l].setD(false);
                                                            }
                                                        }
                                                    }
                                                    break;
                                                case 0:
                                                    if (squares[i - 1][j + 1].isD() && squares[i - 1][j + 1].getColor() == 2 && mf == 0) {
                                                        move(new Canvas(), squares[i][j], squares[i - 1][j + 1], bc);
                                                        squares[i][j].setQueen(true);
                                                    } else if (squares[i + 1][j + 1].isD() && squares[i + 1][j + 1].getColor() == 2 && mf == 0) {
                                                        move(new Canvas(), squares[i][j], squares[i + 1][j + 1], bc);
                                                        squares[i][j].setQueen(true);
                                                    } else if (squares[i - 2][j + 2].isD()) {
                                                        if (squares[i - 1][j + 1].getColor() == 1 && squares[i - 2][j + 2].getColor() == 2) {
                                                            fight(new Canvas(), squares[i][j], squares[i - 1][j + 1], squares[i - 2][j + 2], bc);
                                                            squares[i][j].setQueen(true);
                                                        } else if (squares[i - 1][j + 1].getColor() == 2 && squares[i - 2][j + 2].getColor() == 1)
                                                            fight(new Canvas(), squares[i][j], squares[i - 1][j + 1], squares[i - 2][j + 2], wc);
                                                    } else {
                                                        for (Square[] square : squares) {
                                                            for (int l = 0; l < squares[i].length; l++) {
                                                                if (square[l].isD())
                                                                    square[l].setD(false);
                                                            }
                                                        }
                                                    }
                                                    break;
                                                case 6:
                                                    if (squares[i - 1][j - 1].isD() && squares[i - 1][j - 1].getColor() == 1 && mf == 0) {
                                                        move(new Canvas(), squares[i][j], squares[i - 1][j - 1], wc);
                                                    } else if (squares[i + 1][j - 1].isD() && squares[i + 1][j - 1].getColor() == 1 && mf == 0) {
                                                        move(new Canvas(), squares[i][j], squares[i + 1][j - 1], wc);
                                                    } else if (squares[i - 1][j + 1].isD() && squares[i - 1][j + 1].getColor() == 2 && mf == 0) {
                                                        move(new Canvas(), squares[i][j], squares[i - 1][j + 1], bc);
                                                    } else if (squares[i + 1][j + 1].isD() && squares[i + 1][j + 1].getColor() == 2 && mf == 0) {
                                                        move(new Canvas(), squares[i][j], squares[i + 1][j + 1], bc);
                                                    } else if (squares[i - 2][j - 2].isD()) {
                                                        if (squares[i - 1][j - 1].getColor() == 1 && squares[i - 2][j - 2].getColor() == 2)
                                                            fight(new Canvas(), squares[i][j], squares[i - 1][j - 1], squares[i - 2][j - 2], bc);
                                                        else if (squares[i - 1][j - 1].getColor() == 2 && squares[i - 2][j - 2].getColor() == 1)
                                                            fight(new Canvas(), squares[i][j], squares[i - 1][j - 1], squares[i - 2][j - 2], wc);
                                                    } else {
                                                        for (Square[] square : squares) {
                                                            for (int l = 0; l < squares[i].length; l++) {
                                                                if (square[l].isD())
                                                                    square[l].setD(false);
                                                            }
                                                        }
                                                    }
                                                    break;
                                            }
                                            break;
                                        case 0:
                                            switch (j) {
                                                case 2:
                                                case 4:
                                                    if (squares[i + 1][j - 1].isD() && squares[i + 1][j - 1].getColor() == 1 && mf == 0) {
                                                        move(new Canvas(), squares[i][j], squares[i + 1][j - 1], wc);
                                                    } else if (squares[i + 1][j + 1].isD() && squares[i + 1][j + 1].getColor() == 2 && mf == 0) {
                                                        move(new Canvas(), squares[i][j], squares[i + 1][j + 1], bc);
                                                    } else if (squares[i + 2][j - 2].isD()) {
                                                        if (squares[i + 1][j - 1].getColor() == 1 && squares[i + 2][j - 2].getColor() == 2)
                                                            fight(new Canvas(), squares[i][j], squares[i + 1][j - 1], squares[i + 2][j - 2], bc);
                                                        else if (squares[i + 1][j - 1].getColor() == 2 && squares[i + 2][j - 2].getColor() == 1)
                                                            fight(new Canvas(), squares[i][j], squares[i + 1][j - 1], squares[i + 2][j - 2], wc);
                                                    } else if (squares[i + 2][j + 2].isD()) {
                                                        if (squares[i + 1][j + 1].getColor() == 1 && squares[i + 2][j + 2].getColor() == 2)
                                                            fight(new Canvas(), squares[i][j], squares[i + 1][j + 1], squares[i + 2][j + 2], bc);
                                                        else if (squares[i + 1][j + 1].getColor() == 2 && squares[i + 2][j + 2].getColor() == 1)
                                                            fight(new Canvas(), squares[i][j], squares[i + 1][j + 1], squares[i + 2][j + 2], wc);
                                                    } else {
                                                        for (Square[] square : squares) {
                                                            for (int l = 0; l < squares[i].length; l++) {
                                                                if (square[l].isD())
                                                                    square[l].setD(false);
                                                            }
                                                        }
                                                    }
                                                    break;
                                                case 0:
                                                    if (squares[i + 1][j + 1].isD() && squares[i + 1][j + 1].getColor() == 2 && mf == 0) {
                                                        move(new Canvas(), squares[i][j], squares[i + 1][j + 1], bc);
                                                        squares[i][j].setQueen(true);
                                                    } else if (squares[i + 2][j + 2].isD()) {
                                                        if (squares[i + 1][j + 1].getColor() == 1 && squares[i + 2][j + 2].getColor() == 2) {
                                                            fight(new Canvas(), squares[i][j], squares[i + 1][j + 1], squares[i + 2][j + 2], bc);
                                                            squares[i][j].setQueen(true);
                                                        } else if (squares[i + 1][j + 1].getColor() == 2 && squares[i + 2][j + 2].getColor() == 1)
                                                            fight(new Canvas(), squares[i][j], squares[i + 1][j + 1], squares[i + 2][j + 2], wc);
                                                    } else {
                                                        for (Square[] square : squares) {
                                                            for (int l = 0; l < squares[i].length; l++) {
                                                                if (square[l].isD())
                                                                    square[l].setD(false);
                                                            }
                                                        }
                                                    }
                                                    break;
                                                case 6:
                                                    if (squares[i + 1][j - 1].isD() && squares[i + 1][j - 1].getColor() == 1 && mf == 0) {
                                                        move(new Canvas(), squares[i][j], squares[i + 1][j - 1], wc);
                                                    } else if (squares[i + 1][j + 1].isD() && squares[i + 1][j + 1].getColor() == 2 && mf == 0) {
                                                        move(new Canvas(), squares[i][j], squares[i + 1][j + 1], bc);
                                                    } else if (squares[i + 2][j - 2].isD()) {
                                                        if (squares[i + 1][j - 1].getColor() == 1 && squares[i + 2][j - 2].getColor() == 2)
                                                            fight(new Canvas(), squares[i][j], squares[i + 1][j - 1], squares[i + 2][j - 2], bc);
                                                        else if (squares[i + 1][j - 1].getColor() == 2 && squares[i + 2][j - 2].getColor() == 1)
                                                            fight(new Canvas(), squares[i][j], squares[i + 1][j - 1], squares[i + 2][j - 2], wc);
                                                    } else {
                                                        for (Square[] square : squares) {
                                                            for (int l = 0; l < squares[i].length; l++) {
                                                                if (square[l].isD())
                                                                    square[l].setD(false);
                                                            }
                                                        }
                                                    }
                                                    break;
                                            }
                                            break;
                                        case 7:
                                            switch (j) {
                                                case 3:
                                                case 5:
                                                    if (squares[i - 1][j - 1].isD() && squares[i - 1][j - 1].getColor() == 1 && mf == 0) {
                                                        move(new Canvas(), squares[i][j], squares[i - 1][j - 1], wc);
                                                    } else if (squares[i - 1][j + 1].isD() && squares[i - 1][j + 1].getColor() == 2 && mf == 0) {
                                                        move(new Canvas(), squares[i][j], squares[i - 1][j + 1], bc);
                                                    } else if (squares[i - 2][j - 2].isD()) {
                                                        if (squares[i - 1][j - 1].getColor() == 1 && squares[i - 2][j - 2].getColor() == 2)
                                                            fight(new Canvas(), squares[i][j], squares[i - 1][j - 1], squares[i - 2][j - 2], bc);
                                                        else if (squares[i - 1][j - 1].getColor() == 2 && squares[i - 2][j - 2].getColor() == 1)
                                                            fight(new Canvas(), squares[i][j], squares[i - 1][j - 1], squares[i - 2][j - 2], wc);
                                                    } else if (squares[i - 2][j + 2].isD()) {
                                                        if (squares[i - 1][j + 1].getColor() == 1 && squares[i - 2][j + 2].getColor() == 2)
                                                            fight(new Canvas(), squares[i][j], squares[i - 1][j + 1], squares[i - 2][j + 2], bc);
                                                        else if (squares[i - 1][j + 1].getColor() == 2 && squares[i - 2][j + 2].getColor() == 1)
                                                            fight(new Canvas(), squares[i][j], squares[i - 1][j + 1], squares[i - 2][j + 2], wc);
                                                    } else {
                                                        for (Square[] square : squares) {
                                                            for (int l = 0; l < squares[i].length; l++) {
                                                                if (square[l].isD())
                                                                    square[l].setD(false);
                                                            }
                                                        }
                                                    }
                                                    break;
                                                case 1:
                                                    if (squares[i - 1][j - 1].isD() && squares[i - 1][j - 1].getColor() == 1 && mf == 0) {
                                                        move(new Canvas(), squares[i][j], squares[i - 1][j - 1], wc);
                                                    } else if (squares[i - 1][j + 1].isD() && squares[i - 1][j + 1].getColor() == 2 && mf == 0) {
                                                        move(new Canvas(), squares[i][j], squares[i - 1][j + 1], bc);
                                                    } else if (squares[i - 2][j + 2].isD()) {
                                                        if (squares[i - 1][j + 1].getColor() == 1 && squares[i - 2][j + 2].getColor() == 2)
                                                            fight(new Canvas(), squares[i][j], squares[i - 1][j + 1], squares[i - 2][j + 2], bc);
                                                        else if (squares[i - 1][j + 1].getColor() == 2 && squares[i - 2][j + 2].getColor() == 1)
                                                            fight(new Canvas(), squares[i][j], squares[i - 1][j + 1], squares[i - 2][j + 2], wc);
                                                    } else {
                                                        for (Square[] square : squares) {
                                                            for (int l = 0; l < squares[i].length; l++) {
                                                                if (square[l].isD())
                                                                    square[l].setD(false);
                                                            }
                                                        }
                                                    }
                                                    break;
                                                case 7:
                                                    if (squares[i - 1][j - 1].isD() && squares[i - 1][j - 1].getColor() == 1 && mf == 0) {
                                                        move(new Canvas(), squares[i][j], squares[i - 1][j - 1], wc);
                                                        squares[i][j].setQueen(true);
                                                    } else if (squares[i - 2][j - 2].isD()) {
                                                        if (squares[i - 1][j - 1].getColor() == 1 && squares[i - 2][j - 2].getColor() == 2)
                                                            fight(new Canvas(), squares[i][j], squares[i - 1][j - 1], squares[i - 2][j - 2], bc);
                                                        else if (squares[i - 1][j - 1].getColor() == 2 && squares[i - 2][j - 2].getColor() == 1) {
                                                            fight(new Canvas(), squares[i][j], squares[i - 1][j - 1], squares[i - 2][j - 2], wc);
                                                            squares[i][j].setQueen(true);
                                                        }
                                                    } else {
                                                        for (Square[] square : squares) {
                                                            for (int l = 0; l < squares[i].length; l++) {
                                                                if (square[l].isD())
                                                                    square[l].setD(false);
                                                            }
                                                        }
                                                    }
                                                    break;
                                            }
                                            break;
                                    }
                                }
                                for (Square[] square : squares) {
                                    for (int l = 0; l < squares[i].length; l++) {
                                        if (square[l].isD()) {
                                            square[l].setD(false);
                                        }
                                    }
                                }
                                check++;
                            case 1:
                            case 2:
                                for (Square[] square : squares) {
                                    for (int l = 0; l < squares[i].length; l++) {
                                        if (square[l].isD()) {
                                            square[l].setD(false);
                                        }
                                    }
                                }
                                squares[i][j].setAllow(true);
                                squares[i][j].setD(true);
                                if (who == 1 && check3 == 2 || who == 2 && check3 == 1 || check3 == 0)
                                    check1 = 1;
                                if (t == 1) check3 = 0;
                        }
                    }
                }
            }
        }
        invalidate();
        return true;
    }
}