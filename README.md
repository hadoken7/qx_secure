# qx_secure
##qx加密订阅链接、文本内容

###配置说明
* jar包默认读取位置：/data/qx/qx.json
* qx.json 使用json格式， 
    > plains 为本地文件，urls 为订阅url ，key为密码
###使用说明
- 需要vps、java1.8+环境
- jar包默认使用端口 8080
- 运行: nohup java -jar qx_secure.jar &
- 根据情况修改 qx.json 
- 下载qx_secure.js 到本机，并修改密码，保持qx.json、qx_secure.js内密码相同
###qx相关配置
[general]

resource_parser_url=qx_secure.js

[server_remote]

http://ip:port/qx/plain/0, update-interval=86400, opt-parser=true, enabled=true, tag=Plain

http://ip:port/qx/url/0, update-interval=86400, opt-parser=true, enabled=true, tag=Url
