package bluetoothprinter.jpl;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Image extends BaseJPL
{
	// <summary>
    /// ��ת�Ƕ�
    /// </summary>
    public enum IMAGE_ROTATE
    {
        ANGLE_0,
        ANGLE_90,
        ANGLE_180,
        ANGLE_270
    } 
	public Image(JPL_Param param)
	{
		super(param);
	}
	
	public boolean drawOut(int x, int y, int width, int height, char[] data)
	{
		if(width < 0 || height < 0) 
			return false;
		byte[] cmd = {0x1A, 0x21, 0x00};
		int HeightWriteUnit = 10;
		int WidthByte = ((width-1)/8+1);
		int HeightWrited = 0;
		int HeightLeft = height;
		while(true)
		{
			if(HeightLeft <= HeightWriteUnit)
			{
				port.write(cmd);
				port.write((short)x);
				port.write((short)y);
				port.write((short)width);
				port.write((short)HeightLeft);
				int index = HeightWrited*WidthByte;
				for(int i = 0; i < HeightLeft*WidthByte; i++)
				{
					port.write((byte)data[index++]);
				}
				return true;
			}
			else
			{
				port.write(cmd);
				port.write((short)x);
				port.write((short)y);
				port.write((short)width);
				port.write((short)HeightWriteUnit);
				int index = HeightWrited*WidthByte;
				for(int i = 0; i < HeightWriteUnit*WidthByte; i++)
				{
					port.write((byte)data[index++]);
				}				
				y += HeightWriteUnit;
				HeightWrited += HeightWriteUnit;
				HeightLeft -= HeightWriteUnit; 
			}
		}
	}
	
	public boolean drawOut(int x, int y, int width, int height, byte[] data, boolean Reverse, IMAGE_ROTATE Rotate, int EnlargeX, int EnlargeY)
	{
		if(width < 0 || height < 0) 
			return false;
		
		short ShowType = 0;
		if(Reverse) ShowType |= 0x0001;
		ShowType |= (Rotate.ordinal() << 1)&0x0006;
		ShowType |= (EnlargeX << 8)&0x0F00;
		ShowType |= (EnlargeY << 14)&0xF000;
		
		byte[] cmd = {0x1A, 0x21, 0x01};
		int HeightWriteUnit = 10;
		int WidthByte = ((width-1)/8+1);
		int HeightWrited = 0;
		int HeightLeft = height;
		while(true)
		{
			if(HeightLeft <= HeightWriteUnit)
			{				
				port.write(cmd);
				port.write((short)x);
				port.write((short)y);
				port.write((short)width);
				port.write((short)HeightLeft);
				port.write((short)ShowType);
				int index = HeightWrited*WidthByte;
				for(int i = 0; i < HeightLeft*WidthByte; i++)
				{
					port.write((byte)data[index++]);
				}
				return true;
			}
			else
			{
				port.write(cmd);
				port.write((short)x);
				port.write((short)y);
				port.write((short)width);
				port.write((short)HeightWriteUnit);
				port.write((short)ShowType);
				
				int index = HeightWrited*WidthByte;
				for(int i = 0; i < HeightWriteUnit*WidthByte; i++)
				{
					port.write((byte)data[index++]);
				}				
				y += HeightWriteUnit;
				HeightWrited += HeightWriteUnit;
				HeightLeft -= HeightWriteUnit; 
			}
		}
	}
	  
	private boolean PixelIsBlack(int color, int gray_threshold) {
		int red = ((color & 0x00FF0000) >> 16);
		int green = ((color & 0x0000FF00) >> 8);
		int blue = color & 0x000000FF;
		int grey = (int) ((float) red * 0.299 + (float) green * 0.587 + (float) blue * 0.114);
		return (grey < gray_threshold);
	}
	  
	public byte[] CovertImageHorizontal(Bitmap bitmap, int gray_threshold)
    {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int BytesPerLine = (width - 1) / 8 + 1;

        byte[] data = new byte[BytesPerLine * height];
        for (int i=0;i<data.length;i++)
        {
        	data[i] = 0;
        }
        int index = 0;

        //����λͼ����ĻҶ�ֵ��ȷ����ӡλͼ��Ӧ�ĵ�ĺڰ�ɫ
        int x = 0, y = 0;                                                                          //λͼ��x��y����ֵ��
        for (int i = 0; i < height; i++)                                        //ÿ���ж�һ�����У���Ҫ�ж�BmpHeight��
        {
            for (int j = 0; j < BytesPerLine; j++)                                                    //ÿ����ҪLengthRow�ֽڴ�����ݣ�
            {
                for (int k = 0; k < 8; k++)                                                        //ÿ���ֽڴ��8���㣬��1��1λ��
                {
                    x = (j << 3) + k;                                                              //x����Ϊ�Ѽ����ֽڡ�8+��ǰ�ֽڵ�λk��
                    y = i;                                                                         //��ǰ�У�
                    if (x >= width)                                             //���λͼ��ǰ���ص��y�������ʵ��λͼ���(λͼʵ�ʿ�ȿ��ܲ�Ϊ8��������)�����Ըõ���ɫ�����жϣ�
                    {
                        continue;
                    }
                    else
                    {
                        if (PixelIsBlack(bitmap.getPixel(x, y), gray_threshold))
                        {
                            data[index] |= (byte)(0x01 << k);
                        }
                    }
                }
                index++;                                                       //һ��������8���ж���Ϻ�λͼ����ʵ�ʳ��ȼ�1��
            }
            x = 0;
        }
        return data;
    }


	public boolean drawOut(int x, int y, Resources res, int id, IMAGE_ROTATE rotate)
	{
		Bitmap bitmap = BitmapFactory.decodeResource(res, id);
		int width =bitmap.getWidth();
		int height =bitmap.getHeight();
		if (width > param.pageWidth || height>param.pageHeight)
			return false;
		return drawOut(x,y,width,height,CovertImageHorizontal(bitmap,128),false,rotate,0,0);
	}
}
