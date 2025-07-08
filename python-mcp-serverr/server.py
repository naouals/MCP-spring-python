from mcp.server.fastmcp import FastMCP

mcp=FastMCP("Python MCP server")

@mcp.tool()
def get_employee_info(name:str)-> str:
    """
        get information about a given employee name:
         - name
         - salary
    """
    return {

        "employee_name": name,
        "salary": 8700,

    }