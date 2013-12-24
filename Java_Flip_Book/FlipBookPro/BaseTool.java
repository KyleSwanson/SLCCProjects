package FlipBookPro;

import java.io.Serializable;

public class BaseTool implements Serializable{
	int _id;
	ToolInformation _tool_info;
	
	public BaseTool(int id, ToolInformation tool_info) {
		this._id = id;
		this._tool_info = tool_info;
	}
	
	public void setToolInformation(ToolInformation tool_info) {
		this._tool_info = tool_info;
	}
	
	public ToolInformation getToolInformation() {
		return this._tool_info;
	}
}
