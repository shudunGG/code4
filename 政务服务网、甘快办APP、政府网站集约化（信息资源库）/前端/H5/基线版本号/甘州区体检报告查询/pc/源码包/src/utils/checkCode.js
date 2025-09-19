let code = ""
//得到随机的颜色值
function randomColor () {
    let r = Math.floor(Math.random() * 256);
    let g = Math.floor(Math.random() * 256);
    let b = Math.floor(Math.random() * 256);
    return "rgb(" + r + "," + g + "," + b + ")";
}
function createCode (queryCode) {
    let show_num = []
    let canvas_width = document.getElementById("canvas").width;
    let canvas_height = document.getElementById("canvas").height;
    let canvas = document.getElementById("canvas"); //获取到canvas的对象，演员
    let context = canvas.getContext("2d"); //获取到canvas画图的环境，演员表演的舞台
    canvas.width = canvas_width;
    canvas.height = canvas_height;
    let sCode = queryCode
    //"a,b,c,d,e,f,g,h,i,j,k,m,n,p,q,r,s,t,u,v,w,x,y,z,A,B,C,E,F,G,H,J,K,L,M,N,P,Q,R,S,T,W,X,Y,Z,1,2,3,4,5,6,7,8,9,0";
    let aCode = sCode.split(",");
    // var aLength = aCode.length; //获取到数组的长度
    for (let i = 0; i < 1; i++) {
        let deg = Math.random() * 15 * Math.PI / 180;//产生0~30之间的随机弧度
        let txt = aCode[i]; //得到随机的一个内容
        show_num[i] = txt?.toLowerCase();
        let x = 10 + i * 13; //文字在canvsas上的x坐标
        let y = 20 + Math.random() * 3; //文字在canvas上的y坐标
        context.font = "bold 20px 微软雅黑";
        context.translate(x, y);
        context.rotate(deg);
        context.fillStyle = randomColor();
        context.fillText(txt, 0, 0);
        context.rotate(-deg);
        context.translate(-x, -y);
    }
    for (let i = 0; i <= 5; i++) {
        //验证码上显示线条
        context.strokeStyle = randomColor();
        context.beginPath();
        context.moveTo(
            Math.random() * canvas_width,
            Math.random() * canvas_height
        );
        context.lineTo(
            Math.random() * canvas_width,
            Math.random() * canvas_height
        );
        context.stroke();
    }
    for (let i = 0; i <= 30; i++) {
        //验证码上显示小点
        context.strokeStyle = randomColor();
        context.beginPath();
        let x = Math.random() * canvas_width;
        let y = Math.random() * canvas_height;
        context.moveTo(x, y);
        context.lineTo(x + 1, y + 1);
        context.stroke();
    }
    code = show_num.join("");
}

//校验验证码
function validateCode (inputCode) {
    if (inputCode.length <= 0) { //若输入的验证码长度为0
        return false
    } else if (inputCode !== code) { //若输入的验证码与产生的验证码不一致时
        return false
    } else { //输入正确时
        return true
    }
}
export {
    createCode,
    validateCode,
}