package bluetoothprinter.jpl;

public class Page  extends BaseJPL
{
	public static enum PAGE_ROTATE
	{
		x0,
		x90,
	}
	/*
	 * ���캯��
	 */
	public Page(JPL_Param param) {
		super(param);
	}

	/*
	 * ��ӡҳ�濪ʼ ʹ�ô�ӡ��ȱʡ�������ƴ�ӡ��ҳ�� ȱʡҳ���576dots(72mm),��640dots��80mm��
	 */
	public boolean start() {
		param.pageWidth = 576;
		param.pageHeight = 640;
		byte[] cmd = new byte[] { 0x1A, 0x5B, 0x00 };
		return port.write(cmd);
	}

	/*
	 * ��ӡҳ�濪ʼ
	 */
	public boolean start(int originX, int originY, int pageWidth, int pageHeight, PAGE_ROTATE rotate) 
	{
		if (originX < 0 || originX > 575)
			return false;
		if (originY < 0)
			return false;
		if (pageWidth < 0 || pageWidth > 576)
			return false;
		if (pageHeight < 0 )
			return false;
		param.pageWidth = pageWidth;
		param.pageHeight = pageHeight;
		byte[] cmd = { 0x1A, 0x5B, 0x01 };
		if (!port.write(cmd))
			return false;
		if (!port.write((short) originX))
			return false;
		if (!port.write((short) originY))
			return false;
		if (!port.write((short) pageWidth))
			return false;
		if (!port.write((short) pageHeight))
			return false;
		return port.write((byte) rotate.ordinal());
	}

	/*
	 * ���ƴ�ӡҳ�����
	 */
	public boolean end() {
		byte[] cmd = new byte[] { 0x1A, 0x5D, 0x00 };
		return port.write(cmd);
	}

	/*
	 * ��ӡҳ������ ֮ǰ����ҳ�洦��ֻ�ǰѴ�ӡ���󻭵��ڴ��У�����Ҫͨ��������������ݴ�ӡ����
	 */
	public boolean print() {
		byte[] cmd = new byte[] { 0x1A, 0x4F, 0x00 };
		return port.write(cmd);
	}
	
	/*
	 * ��ǰҳ���ظ���ӡ����
	 */
	public boolean print(int count)
	{
		byte[] cmd = {0x1A, 0x4F, 0x01, 0x00};
		cmd[3] = (byte)count;
		return port.write(cmd);
	}
	
}
