var mysql = require('mysql');
var express = require('express');
var bodyParser = require('body-parser');
const { parse } = require('qs');
var app = express();

//app.use(bodyParser.json({extended: true})); //사용자가 웹사이트로 전달하는 정보들을 검사하는 미들웨어
//app.use(bodyParser.urlencoded({extended: true})); //json이 아닌 post형식으로올때 파서

app.use(bodyParser.json({limit: '50mb'}));
app.use(bodyParser.urlencoded({limit: '50mb', extended: true}));

app.listen(3000, function () {
    console.log('서버 실행 중...');
});

var connection = mysql.createConnection({
    host     : 'database-1.cj61bbiht7nz.us-east-2.rds.amazonaws.com',
    user     : 'tantan_db',
    database : 'tantanDB',
    password : 'tantan21!',
    port     : 3306
});

//목차 - 설정 - 로그인
//목차 - 설정 - 회원가입
//목차 - 설정 -  비밀번호 찾기(임시비밀번호)

//목차 - 설정 - 개인정보 - 닉네임(이름) 변경
//목차 - 설정 - 개인정보 - 탈퇴하기
//목차 - 설정 - 개인정보 - 비밀번호 변경

//목차 - 설정 - 공지사항
//목차 - 설정 - 도움말
//목차 - 설정 - 공지사항 - 상세보기
//목차 - 설정 - 도움말 - 상세보기

//목차 - 설정 - 스마트미러 연결

//목차 - 추가 - 운동
//목차 - 추가 - 물
//목차 - 추가 - 신체
//목차 - 추가 - 식단

//목차 - 커뮤니티/팁 - 식단 팁 가져오기
//목차 - 커뮤니티/팁 - 운동 팁 가져오기

//목차 - 커뮤니티/팁 - 운동 팁 - 상세보기
//목차 - 커뮤니티/팁 - 식단 팁 - 상세보기

//목차 - 달력 - 전체보기 - 가져오기

//목차 - 달력 - 상세보기 - 운동가져오기
//목차 - 달력 - 상세보기 - 물가져오기
//목차 - 달력 - 상세보기 - 신체가져오기
//목차 - 달력 - 상세보기 - 식단가져오기

//목차 - 달력 - 상세보기 - 운동 삭제하기
//목차 - 달력 - 상세보기 - 식단 삭제하기

//목차 - 달력 - 상세보기 - 운동 수정 불러오기
//목차 - 달력 - 상세보기 - 운동 수정 업데이트 하기
//목차 - 달력 - 상세보기 - 식단 수정 불러오기
//목차 - 달력 - 상세보기 - 식단 수정 업데이트 하기

//목차 - 통계/차트 - 신체 - 일주일
//목차 - 통계/차트 - 신체 - 한달
//목차 - 통계/차트 - 신체 - 일년
//목차 - 통계/차트 - 운동 - 일주일, 한달
//목차 - 통계/차트 - 운동 - 일년


//목차 - 설정 - 로그인
app.post('/user/login', function (req, res) {
    var userEmail = req.body.userEmail;
    var userPwd = req.body.userPwd;
    var userName = '';
    var sql = 'SELECT * FROM tantanDB.userTB WHERE userEmail = ?';

    connection.query(sql, userEmail, function (err, result) {
        var resultCode = 404;
        var message = '에러가 발생했습니다';

        if (err) {
            console.log(err);
        } else {

            if (result.length === 0) {
                resultCode = 204;
                message = '존재하지 않는 계정입니다!';
            } else if (userPwd !== result[0].userPw) {
                resultCode = 204;
                message = '비밀번호가 틀렸습니다!';
            } else {
                resultCode = 200;
                message = '로그인 성공! ' + result[0].userName + '님 환영합니다!';
                userName = result[0].userName;
            }
        }

        res.json({
            'code': resultCode,
            'message': message,
            'userName' : userName
        });
    })
});

//목차 - 설정 - 회원가입
app.post('/user/join', function (req, res) {

    var userEmail = req.body.userEmail;
    var userPwd = req.body.userPwd;
    var userName = req.body.userName;

    var sql2 = 'SELECT * FROM tantanDB.userTB WHERE userEmail = ?';
    connection.query(sql2, userEmail, function (err, result) {
        var resultCode = 404;
        var message = '에러가 발생했습니다';

        if(err) {
            console.log(err);

            res.json({
                'code': resultCode,
                'message': message
            });

        } else if (result[0] == null) {
            // 삽입을 수행하는 sql문.
            var sql = 'INSERT INTO tantanDB.userTB (userEmail, userPw, userName) VALUES (?, ?, ?)';
            var params = [userEmail, userPwd, userName];

            connection.query(sql, params, function (err, result) {
                if (err) {
                    console.log(err);

                    res.json({
                        'code': resultCode,
                        'message': message
                    });
        
                } else {
                    resultCode = 200;
                    message = '회원가입에 성공했습니다.';

                    res.json({
                        'code': resultCode,
                        'message': message
                    });
                }
            });

        } else {
            if (userEmail === result[0].userEmail) {
                resultCode = 204;
                message = '존재하는 계정';
            } else {
                resultCode = 206;
                message = '모르는 오류';
            }

            res.json({
                'code': resultCode,
                'message': message
            });
        }
    });
});

//목차 - 설정 -  비밀번호 찾기(임시비밀번호)
app.post('/user/password', function (req, res) {
    var userEmail = req.body.userEmail;
    var userPw = req.body.userPw;

    var resultCode = 0;
    var message = 'anything';

    var sql = 'UPDATE tantanDB.userTB SET userPw = ? WHERE userEmail = ?';
    var params = [userPw,userEmail];

    var sql2 = 'SELECT * FROM tantanDB.userTB WHERE userEmail = ?';

    connection.query(sql2, userEmail, function (err, result) {
        

        if (err) {
            resultCode = 404;
            message = 'select 구문'
            console.log(err);

            res.json({
                'code': resultCode,
                'message': message
            });
        } else {
      
            if (result.length === 0) {
                
                resultCode = 204;
                message = '회원가입 이력이 없습니다.';

                res.json({
                    'code': resultCode,
                    'message': message
                });
            } else {
                connection.query(sql, params, function (err, result) {
            
                    if (err) {
                        resultCode = 404;
                        message = "update 구문 오류"
                        console.log(err);

                        res.json({
                            'code': resultCode,
                            'message': message
                        });
                    } else {
           
                        resultCode = 200;
                        message = '임시비밀번호로 변경 성공! ';

                        res.json({
                            'code': resultCode,
                            'message': message
                        });
            
                    }
        
                })
                
            }
        }
        
    });
    
});

//목차 - 설정 - 개인정보 - 닉네임(이름) 변경
app.post('/user/name', function (req, res) {
    var userEmail = req.body.userEmail;
    var userName = req.body.userName;


    

    var sql = 'UPDATE tantanDB.userTB SET userName = ? WHERE userEmail = ?';
    var params = [userName,userEmail];
    

    connection.query(sql, params, function (err, result) {
        var resultCode = 404;
        var message = '에러가 발생했습니다';

        if (err) {
            console.log(err);
        } else {

            resultCode = 200;
            message = '닉네임 변경 성공! ';

        }

        res.json({
            'code': resultCode,
            'message': message
        });
    })
});

//목차 - 설정 - 개인정보 - 탈퇴하기
app.post('/user/leave', function (req, res) {
    var userEmail = req.body.userEmail;

    var sql2 = 'SELECT * FROM tantanDB.userTB WHERE userEmail = ?';
    connection.query(sql2, userEmail, function (err, result) {
        var resultCode = 404;
        var message = '에러가 발생했습니다';

        if(err) {
            console.log("err01");

            res.json({
                'code': resultCode,
                'message': message
            });

        } else if (result[0] !== undefined) {
            // 삭제를 수행하는 sql문.
            var sql = 'DELETE FROM tantanDB.userTB WHERE userEmail = ?';

            connection.query(sql, userEmail, function (err, result) {
                if (err) {
                    console.log(err);
        
                } else {
                    resultCode = 208;
                    message = '탈퇴에 성공하였습니다.';
                }

                res.json({
                    'code': resultCode,
                    'message': message
                });

            });

        } else {
                resultCode = 206;
                message = '로그인을 하지 않았습니다.';

            res.json({
                'code': resultCode,
                'message': message
            });
        }
        
    });
});


//목차 - 설정 - 개인정보 - 비밀번호 변경
app.post('/user/pwd', function (req, res) {
    var userEmail = req.body.userEmail;
    var userPwd = req.body.userPwd;
    var sql = 'UPDATE tantanDB.userTB SET userPw = ? WHERE userEmail = ?';
    var params = [userPwd, userEmail];


    connection.query(sql, params, function (err, result) {
        var resultCode = 404;
        var message = '에러가 발생했습니다';

        if (err) {
            console.log(err);
        } else {

            resultCode = 200;
            message = '비밀번호 변경 성공!';
        }

        res.json({
            'code': resultCode,
            'message': message
        });
    })
});




//목차 - 추가 - 운동
app.post('/user/add/run', function (req, res) {

    var userEmail = req.body.userEmail;
    var runDate = req.body.runDate;
    var runTime_h = req.body.runTime_h;
    var runTime_m = req.body.runTime_m;
    var runMain = req.body.runMain;
    var runSub = req.body.runSub;

    var sql2 = 'SELECT * FROM tantanDB.userTB WHERE userEmail = ?';
    connection.query(sql2, userEmail, function (err, result) {
        var resultCode = 404;
        var message = '에러가 발생했습니다';

        if(err) {
            console.log(err);

            res.json({
                'code': resultCode,
                'message': message
            });

        } else if (result[0] !== undefined) {
            // 삽입을 수행하는 sql문.
            var sql = 'INSERT INTO tantanDB.addRunTB (runDate,runTime_h,runTime_m,runMain,runSub,userEmail) VALUES (?, ?, ?, ?, ?, ?)';
            var params = [runDate, runTime_h, runTime_m, runMain, runSub, userEmail];

            connection.query(sql, params, function (err, result) {
                if (err) {
                    console.log(err);

                    res.json({
                        'code': resultCode,
                        'message': message
                    });
        
                } else {
                    resultCode = 200;
                    message = '신체 추가 성공';

                    res.json({
                        'code': resultCode,
                        'message': message
                    });
                }
            });

        } else {
                resultCode = 202;
                message = '로그인을 하지 않았습니다.';

                res.json({
                    'code': resultCode,
                    'message': message
                });
        }
    });
});



//목차 - 추가 - 물
app.post('/user/water', function (req, res) {
    console.log("물 추가 들어옴");
    console.log(req.body);
    
    var userEmail = req.body.userEmail;
    var waterDate = req.body.waterDate;
    var waterMl = req.body.waterMl;

    // 삽입을 수행하는 sql문.
    var sql = 'INSERT INTO tantanDB.addWaterTB (waterDate, waterMl, userEmail) VALUES (?, ?, ?)';
    var params = [waterDate, waterMl, userEmail];

    connection.query(sql, params, function (err, result) {
        if (err) {
            console.log(err);
        
        } else {
            resultCode = 212;
            message = '물 추가 성공';
        }

        res.json({
            'code': resultCode,
            'message': message
        });
    });
});




//목차 - 추가 - 신체
app.post('/user/body', function (req, res) {
    var userEmail = req.body.userEmail;
    var bodyDate = req.body.bodyDate;
    var bodyWeight = req.body.bodyWeight;
    var bodyMuscle = req.body.bodyMuscle;
    var bodyFat = req.body.bodyFat;
    var bodyPhoto = req.body.bodyPhoto;
    var bodyPhotoPath = req.body.bodyPhotoPath;

    var sql2 = 'SELECT * FROM tantanDB.addBodyTB WHERE userEmail = ? AND bodyDate = ?';
    var params = [userEmail, bodyDate];
    connection.query(sql2, params, function(err, result) {
        if (err) {

        } else if (result[0] == undefined) {

            var sql = 'INSERT INTO tantanDB.addBodyTB (bodyDate, bodyWeight, bodyMuscle, bodyFat, bodyPhoto, bodyPhotoPath, userEmail) VALUES (?, ?, ?, ?, ?, ?, ?)';
            var params = [bodyDate, bodyWeight, bodyMuscle, bodyFat, bodyPhoto, bodyPhotoPath, userEmail];

            connection.query(sql, params, function (err, result) {
                var resultCode = 404;
                var message = '에러가 발생했습니다';

                if (err) {
                    console.log(err);
                } else {
                    console.log()
                    resultCode = 201;
                    message = '신체 추가 성공!';
                }

                res.json({
                    'code': resultCode,
                    'message': message
                });

            })
        } else if (result[0] !== undefined) {
            var sql = 'UPDATE tantanDB.addBodyTB SET bodyWeight = ?, bodyMuscle = ?, bodyFat = ?, bodyPhoto = ?, bodyPhotoPath = ? WHERE userEmail = ? AND bodyDate = ?';
            var params = [bodyWeight, bodyMuscle, bodyFat, bodyPhoto, bodyPhotoPath, userEmail, bodyDate];

            connection.query(sql, params, function (err, result) {
                var resultCode = 404;
                var message = '에러가 발생했습니다';

                if (err) {
                    console.log(err);
                } else {
                    console.log()
                    resultCode = 202;
                    message = '신체 업데이트 성공!';
                }

                res.json({
                    'code': resultCode,
                    'message': message
                });

            })
        }

    })
    
});


//목차 - 추가 - 식단
app.post('/user/meal', function (req, res) {
    var userEmail = req.body.userEmail;
    var mealDate = req.body.mealDate;
    var mealTime = req.body.mealTime;
    var mealPicture = req.body.mealPicture;
    var mealPicturePath = req.body.mealPicturePath;
    var mealMemo = req.body.mealMemo;


    var sql = 'INSERT INTO tantanDB.addMealTB (mealDate, mealTime, mealPicture, mealPicturePath, mealMemo, userEmail) VALUES (?, ?, ?, ?, ?, ?);'
    var params = [mealDate, mealTime, mealPicture, mealPicturePath, mealMemo, userEmail];

    connection.query(sql, params, function (err, result) {
        var resultCode = 404;
        var message = '에러가 발생했습니다';

        if (err) {
            console.log(err);
        } else {

            resultCode = 200;
            message = '식단 추가 성공';

        }
        res.json({
            'code': resultCode,
            'message': message
        });
    })
});

//목차 - 달력 - 전체보기 - 가져오기
app.post('/user/data/total', function (req, res) {
    var userEmail = req.body.userEmail;
    var selectMonth = req.body.selectDate;

    var dotDate = [];

    var sql = 'SELECT bodyDate FROM tantanDB.addBodyTB where userEmail = ? and month(bodyDate) = ? union SELECT mealDate FROM tantanDB.addMealTB where userEmail = ? and month(mealDate) = ? union SELECT runDate FROM tantanDB.addRunTB where userEmail = ? and month(runDate) = ? union SELECT waterDate FROM tantanDB.addWaterTB where userEmail = ? and month(waterDate) = ?';
    var params = [userEmail,selectMonth,userEmail,selectMonth,userEmail,selectMonth,userEmail,selectMonth];

    connection.query(sql, params, function (err, result) {
        var arrLength = 0;
        var resultCode = 404;
        var message = '에러가 발생했습니다';

        if (err) {

            console.log(err);

            res.json({
                'code': resultCode,
                'message': message,
                'arrLength' : arrLength,
                'result': result
            });
            
        } else {
            
            if (result.length === 0) {
                resultCode = 202;
                arrLength = 0;
                message = '해당 월에 데이터 없음';

                console.log(resultCode);

                res.json({
                    'code': resultCode,
                    'message': message,
                    'arrLength' : arrLength,
                    'result': ""
                });
            } else {

                arrLength = result.length;
                resultCode = 200;
                message = '데이터 가져오기 성공';

                for(var i = 0; i < arrLength; i++){
                    
                    dotDate[i] = result[i].bodyDate;
                    //d_runID += String(result[i].runID) + "/";
                    
                }

                console.log(resultCode);
                console.log(dotDate);

                res.json({
                    'code': resultCode,
                    'message': message,
                    'arrLength' : arrLength,
                    'result': dotDate

                });
            }
        }

    })
});


//목차 - 달력 - 상세보기 - 운동가져오기
app.post('/user/data/run', function (req, res) {
    var userEmail = req.body.userEmail;
    var runDate = req.body.selectDate;
    var runTime_h = [];
    var runTime_m = [];
    var runTime = [];

    var sql = 'SELECT * FROM tantanDB.addRunTB WHERE runDate = ? AND userEmail = ? order by runID';
    var params = [runDate, userEmail];

    connection.query(sql, params, function (err, result) {
        var arrLength = 0;
        var resultCode = 404;
        var message = '에러가 발생했습니다';

        var d_runTime = "";
        var d_runMain = "";
        var d_runSub = "";
        var d_runID = "";

        if (err) {

            console.log(err);

            res.json({
                'code': resultCode,
                'message': message,
                'arrLength' : arrLength,
                'result': result
            });
            
        } else {
            
            if (result.length === 0) {
                resultCode = 202;
                arrLength = 0;
                message = '해당 날짜에 데이터 없음';

                console.log(resultCode);

                res.json({
                    'code': resultCode,
                    'message': message,
                    'arrLength' : arrLength,
                    'result': ""
                });
            } else {

                arrLength = result.length;
                resultCode = 200;
                message = '데이터 가져오기 성공';

                for(var i = 0; i < arrLength; i++){

                    //d_runTime += result[i].runTime + "/";
                    d_runMain += result[i].runMain + "/";
                    d_runSub += result[i].runSub + "/";
                    d_runID += String(result[i].runID) + "/";
                    
                }

                for (var i=0; i<result.length; i++) {
                    runTime_h[i] = result[i].runTime_h;
                    runTime_m[i] = result[i].runTime_m;
                    runTime[i] = runTime_h[i] + runTime_m[i];
                }

                console.log(resultCode);

                res.json({
                    'code': resultCode,
                    'message': message,
                    'arrLength' : arrLength,
                    'runTime' : runTime,
                    'runMain' : d_runMain,
                    'runSub' : d_runSub,
                    'runID' : d_runID
                });
            }
        }

    })
});

//목차 - 달력 - 상세보기 - 물가져오기
app.post('/user/data/water', function (req, res) {
    var userEmail = req.body.userEmail;
    var waterDate = req.body.selectDate;

    var sql = 'SELECT * FROM tantanDB.addWaterTB WHERE waterDate = ? AND userEmail = ?';
    var params = [waterDate, userEmail];

    connection.query(sql, params, function (err, result) {
        var arrLength = 0;
        var j = 0;
        var waterTotal = 0;
        var resultCode = 404;
        var message = '에러가 발생했습니다';

        if (err) {
        
            console.log(err);
            
        } else {
            
            if (result.length === 0) {
                resultCode = 202;
                arrLength = 0;
                waterTotal = 0;
                message = '해당 날짜에 데이터 없음';
            } else {

                arrLength = result.length;

                for(var i = 0; i < arrLength; i++){
                    j += result[i].waterMl;
                }

                waterTotal = String(j);

                resultCode = 200;
                message = '데이터 가져오기 성공';
            }
        }

        res.json({
            'code': resultCode,
            'message': message,
            'arrLength' : arrLength,
            'result': waterTotal
        });
    })
});

//목차 - 달력 - 상세보기 - 신체가져오기
app.post('/user/data/body', function (req, res) {
    var userEmail = req.body.userEmail;
    var bodyDate = req.body.selectDate;

    var sql = 'SELECT * FROM tantanDB.addBodyTB WHERE bodyDate = ? AND userEmail = ?';
    var params = [bodyDate, userEmail];


    connection.query(sql, params, function (err, result) {
        var resultCode = 404;
        var message = '에러가 발생했습니다';

        if (err) {
            console.log(err);
        
        } else if (result[0] !== undefined) {
            resultCode = 200;
            message = '신체 출력 확인';

            if (result[0].bodyPhoto !== undefined){

                res.json({
                    'code': resultCode,
                    'message': message,
                    'bodyweight': result[0].bodyWeight,
                    'bodymuscle': result[0].bodyMuscle,
                    'bodyfat': result[0].bodyFat,
                    'bodyphoto': result[0].bodyPhoto
                });

            }else{
                resultCode = 202;
                message = '신체 출력 확인_사진없음';

                res.json({
                    'code': resultCode,
                    'message': message,
                    'bodyweight': result[0].bodyWeight,
                    'bodymuscle': result[0].bodyMuscle,
                    'bodyfat': result[0].bodyFat,
                    'bodyphoto': "nullPhoto"
                });

            }

            

        } else if (result[0] == undefined) {
            resultCode = 400;
            message = '신체 데이터 없음';

            res.json({
                'code': resultCode,
                'message': message,
            });
        }

    })
});

//목차 - 달력 - 상세보기 - 식단가져오기
app.post('/user/data/meal', function (req, res) {
    var userEmail = req.body.userEmail;
    var mealDate = req.body.selectDate;
    var mealTime = [];
    var mealPicture = [];
    var mealMemo = [];

    var sql = 'SELECT * FROM tantanDB.addMealTB WHERE mealDate = ? AND userEmail = ? ORDER BY mealTime';
    var params = [mealDate, userEmail];

    connection.query(sql, params, function (err, result) {
        var resultCode = 404;
        var message = '에러가 발생했습니다';

        if (err) {
            console.log(err);
        
        } else if (result[0] !== undefined) {

            resultCode = 200;
            message = '식단 출력 확인';

            arrLength = result.length;

            for(var i = 0; i < arrLength; i++) {
                mealTime[i] = result[i].mealTime;
                mealMemo[i] = result[i].mealMemo;
                mealPicture[i] = result[i].mealPicture;
            }

            res.json({
                'code': resultCode,
                'message': message,
                'result' : arrLength,
                'mealTime': mealTime,
                'mealMemo': mealMemo,
                'mealPicture': mealPicture
            });

        } else if (result[0] == undefined) {
            resultCode = 400;
            message = '식단 데이터 없음';

            res.json({
                'code': resultCode,
                'message': message,
            });
        }

    })
});

//목차 - 커뮤니티/팁 - 식단 팁 가져오기
app.post('/user/data/community/meal', function (req, res) {
    var userEmail = req.body.userEmail;

    var userAuto = 0;
    var arrLength = 0;

    var tipTitle = '';

    var sql = 'SELECT * FROM tantanDB.userTB WHERE userEmail = ?';

    connection.query(sql, userEmail, function (err, result) {
        
        var resultCode = 404;
        var message = '에러가 발생했습니다';

        if (err) {
            
            console.log(err);

            res.json({
                'code': resultCode,
                'message': message,
                'tipTitle' : tipTitle,
                'userAuto' : userAuto,
                'arrLength' : arrLength
            });

        } else {

            userAuto = result[0].userAuto;

            var sql2 = 'SELECT * FROM tantanDB.communityTB where tipType = 1 order by tipID';

            connection.query(sql2, function(err, result){

                if(err){
                    console.log(err);

                    res.json({
                        'code': resultCode,
                        'message': message,
                        'tipTitle' : tipTitle,
                        'userAuto' : userAuto,
                        'arrLength' : arrLength
                    });

                }else{
                    arrLength = result.length;

                    if (arrLength === 0) {
                        resultCode = 202;
                        arrLength = 0;
                        message = '식단 커뮤니티가 없습니다.';
    

                        res.json({
                            'code': resultCode,
                            'message': message,
                            'tipTitle' : tipTitle,
                            'userAuto' : userAuto,
                            'arrLength' : arrLength
                        });
        
        
                    }else{

                        for(var i = 0; i < arrLength; i++){
                            tipTitle += result[i].tipTitle + '/';
                        }

                        resultCode = 200;
                        
                        message = '식단 커뮤니티 불러오기 성공'


                        res.json({
                            'code': resultCode,
                            'message': message,
                            'tipTitle' : tipTitle,
                            'userAuto' : userAuto,
                            'arrLength' : arrLength
                        });

                    }

                }
            })

        }

        

    });
});

//목차 - 커뮤니티/팁 - 운동 팁 가져오기
app.post('/user/data/community/run', function (req, res) {
    var userEmail = req.body.userEmail;

    var userAuto = 0;
    var arrLength = 0;

    var tipTitle = '';

    var sql = 'SELECT * FROM tantanDB.userTB WHERE userEmail = ?';

    connection.query(sql, userEmail, function (err, result) {
        
        var resultCode = 404;
        var message = '에러가 발생했습니다';

        if (err) {
            
            console.log(err);

            res.json({
                'code': resultCode,
                'message': message,
                'tipTitle' : tipTitle,
                'userAuto' : userAuto,
                'arrLength' : arrLength
            });

        } else {

            userAuto = result[0].userAuto;

            var sql2 = 'SELECT * FROM tantanDB.communityTB where tipType = 2 order by tipID';

            connection.query(sql2, function(err, result){

                if(err){
                    console.log(err);

                    res.json({
                        'code': resultCode,
                        'message': message,
                        'tipTitle' : tipTitle,
                        'userAuto' : userAuto,
                        'arrLength' : arrLength
                    });

                }else{
                    arrLength = result.length;

                    if (arrLength === 0) {
                        resultCode = 202;
                        arrLength = 0;
                        message = '운동 커뮤니티가 없습니다.';
        
                        console.log(resultCode);

                        res.json({
                            'code': resultCode,
                            'message': message,
                            'tipTitle' : tipTitle,
                            'userAuto' : userAuto,
                            'arrLength' : arrLength
                        });
        
        
                    }else{

                        for(var i = 0; i < arrLength; i++){
                            tipTitle += result[i].tipTitle + '/';
                        }

                        resultCode = 200;
                           
                        message = '운동 커뮤니티 불러오기 성공'

                        res.json({
                            'code': resultCode,
                            'message': message,
                            'tipTitle' : tipTitle,
                            'userAuto' : userAuto,
                            'arrLength' : arrLength
                        });

                    }

                }
            })

        }

        

    });
});


//목차 - 커뮤니티/팁 - 식단 팁 - 상세보기
app.post('/user/data/community/meal/detail', function (req, res) {
    var tipNum = req.body.selectNum;

    var tipTitle = '';
    var tipEdit = '';
    var tipDate = '';
    var tipText = '';

    var sql = 'SELECT * FROM tantanDB.communityTB where tipType = 1 order by tipID limit ?,1';

    connection.query(sql, tipNum, function (err, result) {
        
        var resultCode = 404;
        var message = '에러가 발생했습니다';

        if (err) {

            console.log(err);

            res.json({
                'code': resultCode,
                'message': message,
                'tipTitle' : tipTitle,
                'tipEdit' : tipEdit,
                'tipDate' : tipDate,
                'tipText' : tipText
            });

        } else {
            resultCode = 200;
            message = 'meal detail 가져오기 성공';

            tipTitle = result[0].tipTitle;
            tipEdit = result[0].tipEditorName;
            tipDate = result[0].tipDate;
            tipText = result[0].tipText;

            res.json({
                'code': resultCode,
                'message': message,
                'tipTitle' : tipTitle,
                'tipEdit' : tipEdit,
                'tipDate' : tipDate,
                'tipText' : tipText
            });



        }

        

    });
});

//목차 - 커뮤니티/팁 - 운동 팁 - 상세보기
app.post('/user/data/community/run/detail', function (req, res) {
    var tipNum = req.body.selectNum;

    var tipTitle = '';
    var tipEdit = '';
    var tipDate = '';
    var tipText = '';

    var sql = 'SELECT * FROM tantanDB.communityTB where tipType = 2 order by tipID limit ?,1';

    connection.query(sql, tipNum, function (err, result) {
        
        var resultCode = 404;
        var message = '에러가 발생했습니다';

        if (err) {
            
            console.log(err);

            res.json({
                'code': resultCode,
                'message': message,
                'tipTitle' : tipTitle,
                'tipEdit' : tipEdit,
                'tipDate' : tipDate,
                'tipText' : tipText
            });

        } else {
            resultCode = 200;
            message = 'meal detail 가져오기 성공';

            tipTitle = result[0].tipTitle;
            tipEdit = result[0].tipEditorName;
            tipDate = result[0].tipDate;
            tipText = result[0].tipText;

            res.json({
                'code': resultCode,
                'message': message,
                'tipTitle' : tipTitle,
                'tipEdit' : tipEdit,
                'tipDate' : tipDate,
                'tipText' : tipText
            });



        }

        

    });
});


//목차 - 설정 - 공지사항
app.post('/user/data/notice', function (req, res) {
    var userEmail = req.body.userEmail;

    var userAuto = 0;
    var arrLength = 0;


    var noticeTitle = '';
    var noticeDate = '';
    var noticePri = '';

    var sql = 'SELECT * FROM tantanDB.userTB WHERE userEmail = ?';

    connection.query(sql, userEmail, function (err, result) {
        
        var resultCode = 404;
        var message = '에러가 발생했습니다';

        if (err) {

            console.log(err);

            res.json({
                'code': resultCode,
                'message': message,
                'noticeTitle' : noticeTitle,
                'noticeDate' : noticeDate,
                'noticePri' : noticePri,
                'userAuto' : userAuto,
                'arrLength' : arrLength
            });

        } else {

            userAuto = result[0].userAuto;
            
            var sql2 = 'SELECT * FROM tantanDB.noticeTB order by noticePri asc, noticeID desc';

            connection.query(sql2, function(err, result){

                if(err){
                    console.log(err);

                    res.json({
                        'code': resultCode,
                        'message': message,
                        'noticeTitle' : noticeTitle,
                        'noticeDate' : noticeDate,
                        'noticePri' : noticePri,
                        'userAuto' : userAuto,
                        'arrLength' : arrLength
                    });

                }else{
                    arrLength = result.length;

                    if (arrLength === 0) {
                        resultCode = 202;
                        arrLength = 0;
                        message = '공지사항이 없습니다.';
    

                        res.json({
                            'code': resultCode,
                            'message': message,
                            'noticeTitle' : noticeTitle,
                            'noticeDate' : noticeDate,
                            'noticePri' : noticePri,
                            'userAuto' : userAuto,
                            'arrLength' : arrLength
                        });
        
        
                    }else{

                        for(var i = 0; i < arrLength; i++){
                            noticeTitle += result[i].noticeTitle + '/';
                            noticeDate += result[i].noticeDate + '/';
                            noticePri += String(result[i].noticePri) + '/';
                        }

                        resultCode = 200;
                        
                        message = '공지사항 불러오기 성공'

                        res.json({
                            'code': resultCode,
                            'message': message,
                            'noticeTitle' : noticeTitle,
                            'noticeDate' : noticeDate,
                            'noticePri' : noticePri,
                            'userAuto' : userAuto,
                            'arrLength' : arrLength
                        });

                    }

                }
            })

        }

        

    });
});

//목차 - 설정 - 공지사항 - 상세보기
app.post('/user/data/notice/detail', function (req, res) {
    var noticeNum = req.body.selectNum;

    //select * from noticeTB order by noticePri limit 0,1

    var sql = 'SELECT * FROM tantanDB.noticeTB order by noticePri asc, noticeID desc limit ?,1';

    var noticeTitle = '';
    var noticeDate = '';
    var noticeText = '';
    var noticePri = 0;


    connection.query(sql, noticeNum, function (err, result) {
        var resultCode = 404;
        var message = '에러가 발생했습니다';

        if (err) {
            console.log(err);

            res.json({
                'code': resultCode,
                'message': message,
                'noticeTitle' : noticeTitle,
                'noticeDate' : noticeDate,
                'noticeText' : noticeText,
                'noticePri' : noticePri
    
            });
        } else {
            resultCode = 200;
            message = 'notice detail data 가져오기 성공';

            noticeTitle = result[0].noticeTitle;
            noticeDate = result[0].noticeDate;
            noticeText = result[0].noticeText;
            noticePri = result[0].noticePri;


            res.json({
                'code': resultCode,
                'message': message,
                'noticeTitle' : noticeTitle,
                'noticeDate' : noticeDate,
                'noticeText' : noticeText,
                'noticePri' : noticePri
    
            });
            
        }


    })
});

//목차 - 설정 - 도움말
app.post('/user/data/help', function (req, res) {
    var userEmail = req.body.userEmail;

    var userAuto = 0;
    var arrLength = 0;
    var helpTitle = '';

    var sql = 'SELECT * FROM tantanDB.userTB WHERE userEmail = ?';

    connection.query(sql, userEmail, function (err, result) {
        
        var resultCode = 404;
        var message = '에러가 발생했습니다';

        if (err) {
        
            console.log(err);

            res.json({
                'code': resultCode,
                'message': message,
                'helpTitle' : helpTitle,
                'userAuto' : userAuto,
                'arrLength' : arrLength,
            });

        } else {

            userAuto = result[0].userAuto;
            
            var sql2 = 'SELECT * FROM tantanDB.faqTB order by faqID';

            connection.query(sql2, function(err, result){

                if(err){
                    console.log(err);

                    res.json({
                        'code': resultCode,
                        'message': message,
                        'helpTitle' : helpTitle,
                        'userAuto' : userAuto,
                        'arrLength' : arrLength,
                    });

                }else{
                    arrLength = result.length;

                    if (arrLength === 0) {
                        resultCode = 202;
                        arrLength = 0;
                        message = '도움말이 없습니다.';


                        res.json({
                            'code': resultCode,
                            'message': message,
                            'helpTitle' : helpTitle,
                            'userAuto' : userAuto,
                            'arrLength' : arrLength,
                        });
        
        
                    }else{

                        for(var i = 0; i < arrLength; i++){
                            helpTitle += result[i].faqTitle + '/';
                        }

                        resultCode = 200;
                        
                        message = '도움말 불러오기 성공'

                        res.json({
                            'code': resultCode,
                            'message': message,
                            'helpTitle' : helpTitle,
                            'userAuto' : userAuto,
                            'arrLength' : arrLength,
                        });

                    }

                }
            })

        }

        

    });
});





//목차 - 설정 - 도움말 - 상세보기
app.post('/user/data/help/detail', function (req, res) {
    var helpNum = req.body.selectNum;

    var sql = 'SELECT * FROM tantanDB.faqTB order by faqID limit ?,1';

    var faqTitle = '';
    var faqText = '';

    connection.query(sql, helpNum, function (err, result) {
        var resultCode = 404;
        var message = '에러가 발생했습니다';

        if (err) {
            console.log(err);

            res.json({
                'code': resultCode,
                'message': message,
                'faqTitle' : faqTitle,
                'faqText' : faqText
    
            });
        } else {
            resultCode = 200;
            message = 'help detail data 가져오기 성공';

            faqTitle = result[0].faqTitle;
            faqText = result[0].faqText;

            res.json({
                'code': resultCode,
                'message': message,
                'faqTitle' : faqTitle,
                'faqText' : faqText
    
            });
            
        }


    })
});

//목차 - 설정 - 스마트미러 연결
app.post('/user/data/connect', function (req, res) {
    var userEmail = req.body.userEmail;
    var randomNum = req.body.randomNum;

    var sql = 'SELECT connectID FROM tantanDB.connectTB where randomNum = ? order by connectID';
    var param = randomNum;

    var sql2 = 'UPDATE tantanDB.connectTB SET userEmail = ? WHERE connectID = ?'

    connection.query(sql, param, function (err, result) {

        if (err) {
            resultCode = 404;
            message = 'select구문 에러'
            console.log(err);

            res.json({
                'code': resultCode,
                'message': message
            });
        } else {
      
            if (result.length === 0) {
                
                resultCode = 204;
                message = '해당 랜덤값 없음.';

                console.log(message);

                res.json({
                    'code': resultCode,
                    'message': message
                });
            } else {
                arrLen = result.length;
                conID = result[arrLen - 1].connectID;
                var params = [userEmail,conID];

                connection.query(sql2, params, function (err, result) {
            
                    if (err) {
                        resultCode = 404;
                        message = "update 구문 에러"
                        console.log(err);

                        res.json({
                            'code': resultCode,
                            'message': message
                        });
                    } else {
           
                        resultCode = 200;
                        message = '스마트미러 연결 성공 ';

                        console.log(message);

                        res.json({
                            'code': resultCode,
                            'message': message
                        });
            
                    }
        
                })
                
            }
        }
    })
});



//목차 - 통계/차트 - 운동 - 일주일, 한달
app.post('/user/stats/time', function (req, res) {

    var userEmail = req.body.userEmail;
    var startDate = req.body.dateStart;
    var endDate = req.body.dateEnd;

    var runTime = [];
    var runDate = [];


    var params = [userEmail, startDate, endDate];

    var sql2 = 'SELECT userEmail, sum(runTime_h)+sum(runTime_m) as runTime, runDate FROM tantanDB.addRunTB WHERE userEmail = ? and runDate between ? and ? group by runDate ORDER BY runDate asc';
    
    connection.query(sql2, params, function (err, result) {
        var resultCode = 404;
        var message = '에러가 발생했습니다';

        if (err) {
            console.log(err);
        
        } else {
            resultCode = 200;
            message = '통계 출력 확인';

            if(result.length === 0){
                resultCode = 204;
                message = '통계 없음';
            }else{
                for (var i=0; i<result.length; i++) {
                    runDate[i] = result[i].runDate;
                    runTime[i] = result[i].runTime;
                }
            }

            
        }

        
        res.json({
            'code': resultCode,
            'message': message,
            'result': result.length,
            'runTime': runTime,
            'runDate' : runDate
        });

    });
});

//목차 - 통계/차트 - 운동 - 일년
app.post('/user/stats/time/year', function (req, res) {

    var userEmail = req.body.userEmail;
    var startDate = req.body.dateStart;
    var endDate = req.body.dateEnd;

    var runTime = [];
    var runDate = [];

    var params = [userEmail, startDate, endDate];

    var sql2 = 'SELECT runDate, sum(runTime_h)+sum(runTime_m) as totalTime FROM tantanDB.addRunTB where userEmail = ? and runDate between ? and ? group by Left(runDate,7) ORDER BY runDate asc';
    
    connection.query(sql2, params, function (err, result) {
        var resultCode = 404;
        var message = '에러가 발생했습니다';

        if (err) {
            console.log(err);
        
        } else {
            resultCode = 200;
            message = '통계 출력 확인';

            if(result.length === 0){
                resultCode = 204;
                message = '통계 없음';
            }else{
                for (var i=0; i<result.length; i++) {
                    runDate[i] = result[i].runDate;
                    runTime[i] = result[i].runTime;
                }
            }

        }
        
        res.json({
            'code': resultCode,
            'message': message,
            'result': result.length,
            'runTime': runTime,
            'runDate' : runDate
        });

    });
});


function date_to_str(format)

{

    var year = format.getFullYear();

    var month = format.getMonth() + 1;

    if(month<10) month = '0' + month;

    var date = format.getDate();

    if(date<10) date = '0' + date;


    return year + "-" + month + "-" + date ;


}

//목차 - 통계/차트 - 신체 - 일주일
app.post('/user/stats/week', function (req, res) {

    var userEmail = req.body.userEmail;
    var startDate = req.body.dateStart;
    var endDate = req.body.dateEnd;
    
    var params = [userEmail, startDate, endDate];

    var bodydate = [];
    var bodyweight = [];
    var bodymuscle = [];
    var bodyfat = [];

    var sql = 'SELECT bodyDate, bodyWeight, bodyMuscle, bodyFat FROM tantanDB.addBodyTB WHERE userEmail = ? and bodyDate between ? and ? ORDER BY bodyDate asc';
    
    connection.query(sql, params, function (err, result) {
        var resultCode = 404;
        var message = '에러가 발생했습니다';

        if (err) {
            console.log(err);

            res.json({
                'code': resultCode,
                'message': message
            });
        
        } else {
            if(result.length === 0){
                resultCode = 204;
                message = '통계 값 없음'

                res.json({
                    'code': resultCode,
                    'message': message
                });

            }else{
                resultCode = 200;
                message = '통계 출력 확인';

                for (var i=0; i<result.length; i++) {
                    bodydate[i] = result[i].bodyDate;
                    bodyweight[i] = result[i].bodyWeight;
                    bodymuscle[i] = result[i].bodyMuscle;
                    bodyfat[i] = result[i].bodyFat;
                }

                res.json({
                    'code': resultCode,
                    'message': message,
                    'arrLen': result.length,
                    'bodyDate': bodydate,
                    'bodyWeight': bodyweight,
                    'bodyMuscle': bodymuscle,
                    'bodyFat': bodyfat
                });
            }

        }
        
        


    });
});

//목차 - 통계/차트 - 신체 - 한달
app.post('/user/stats/month', function (req, res) {
    var userEmail = req.body.userEmail;
    var startDate = req.body.dateStart;
    var endDate = req.body.dateEnd;
    
    var params = [userEmail, startDate, endDate];

    var bodydate = [];
    var bodyweight = [];
    var bodymuscle = [];
    var bodyfat = [];

    var sql = 'SELECT bodyDate, bodyWeight, bodyMuscle, bodyFat FROM tantanDB.addBodyTB WHERE userEmail = ? and bodyDate between ? and ? ORDER BY bodyDate asc';
    
    connection.query(sql, params, function (err, result) {
        var resultCode = 404;
        var message = '에러가 발생했습니다';

        if (err) {
            console.log(err);
        
        } else {
            if(result.length === 0){
                resultCode = 204;
                message = '통계 값 없음'

                res.json({
                    'code': resultCode,
                    'message': message
                });
            }else{
                resultCode = 200;
                message = '통계 출력 확인';


                for (var i=0; i<result.length; i++) {
                    bodydate[i] = result[i].bodyDate;
                    bodyweight[i] = result[i].bodyWeight;
                    bodymuscle[i] = result[i].bodyMuscle;
                    bodyfat[i] = result[i].bodyFat;
                }

                res.json({
                    'code': resultCode,
                    'message': message,
                    'arrLen': result.length,
                    'bodyDate': bodydate,
                    'bodyWeight': bodyweight,
                    'bodyMuscle': bodymuscle,
                    'bodyFat': bodyfat
                });
            }
        }
        
    });


});


//목차 - 통계/차트 - 신체 - 일년
app.post('/user/stats/year', function (req, res) {
    var userEmail = req.body.userEmail;
    var startDate = req.body.dateStart;
    var endDate = req.body.dateEnd;
    
    var params = [userEmail, startDate, endDate];

    var bodydate = [];
    var bodyweight = [];
    var bodymuscle = [];
    var bodyfat = [];

    var sql = 'SELECT bodyDate,round(avg(bodyWeight),2) as bodyWeight , round(avg(bodyMuscle),2) as bodyMuscle, round(avg(bodyFat) , 2) as bodyFat FROM tantanDB.addBodyTB where userEmail = ? and bodyDate between ? and ? group by Left( bodyDate,7) ORDER BY bodyDate asc';
    
    connection.query(sql, params, function (err, result) {
        var resultCode = 404;
        var message = '에러가 발생했습니다';

        if (err) {
            console.log(err);
        
        }  else {
            if(result.length === 0){
                resultCode = 204;
                message = '통계 값 없음'

                res.json({
                    'code': resultCode,
                    'message': message
                });
            }else{
                resultCode = 200;
                message = '통계 출력 확인';


                for (var i=0; i<result.length; i++) {
                    bodydate[i] = result[i].bodyDate;
                    bodyweight[i] = result[i].bodyWeight;
                    bodymuscle[i] = result[i].bodyMuscle;
                    bodyfat[i] = result[i].bodyFat;
                }

                res.json({
                    'code': resultCode,
                    'message': message,
                    'arrLen': result.length,
                    'bodyDate': bodydate,
                    'bodyWeight': bodyweight,
                    'bodyMuscle': bodymuscle,
                    'bodyFat': bodyfat
                });
            }
        }

    });


});



//목차 - 달력 - 상세보기 - 운동 삭제하기
app.post('/user/data/run/delete', function (req, res) {
    var userEmail = req.body.userEmail;
    var runDate = req.body.selectDate;
    var selectNum = req.body.selectNum;

    var sql = 'SELECT runID FROM tantanDB.addRunTB where runDate = ? and userEmail = ? order by runID limit ? ,1';
    var params = [runDate, userEmail, selectNum];

    var sql2 = 'DELETE FROM tantanDB.addRunTB where runID = ?'

    connection.query(sql, params, function (err, result) {

        if (err) {
            resultCode = 404;
            message = 'select 에러'
            console.log(err);

            res.json({
                'code': resultCode,
                'message': message
            });
        } else {
                var runID = result[0].runID;

                connection.query(sql2, runID, function (err, result) {
            
                    if (err) {
                        resultCode = 404;
                        message = "delete 실패"
                        console.log(err);

                        res.json({
                            'code': resultCode,
                            'message': message
                        });
                    } else {
           
                        resultCode = 200;
                        message = 'delete 성공';

                        console.log(message);

                        res.json({
                            'code': resultCode,
                            'message': message
                        });
            
                    }
        
                })
            
        }
    })
});

//목차 - 달력 - 상세보기 - 식단 삭제하기
app.post('/user/data/meal/delete', function (req, res) {
    var userEmail = req.body.userEmail;
    var mealDate = req.body.selectDate;
    var selectNum = req.body.selectNum;

    var sql = 'SELECT mealID FROM tantanDB.addMealTB where mealDate = ? and userEmail = ? order by mealID limit ? ,1';
    var params = [mealDate, userEmail, selectNum];

    var sql2 = 'DELETE FROM tantanDB.addMealTB where mealID = ?'

    connection.query(sql, params, function (err, result) {

        if (err) {
            resultCode = 404;
            message = 'select 에러'
            console.log(err);

            res.json({
                'code': resultCode,
                'message': message
            });
        } else {
                var mealID = result[0].mealID;

                connection.query(sql2, mealID, function (err, result) {
            
                    if (err) {
                        resultCode = 404;
                        message = "delete 실패"
                        console.log(err);

                        res.json({
                            'code': resultCode,
                            'message': message
                        });
                    } else {
           
                        resultCode = 200;
                        message = 'delete 성공';

                        console.log(message);

                        res.json({
                            'code': resultCode,
                            'message': message
                        });
            
                    }
        
                })
            
        }
    })
});

//목차 - 달력 - 상세보기 - 운동 수정 불러오기
app.post('/user/data/run/modify', function (req, res) {
    var userEmail = req.body.userEmail;
    var runDate = req.body.selectDate;
    var selectNum = req.body.selectNum;

    var sql = 'SELECT runID FROM tantanDB.addRunTB where runDate = ? and userEmail = ? order by runID limit ? ,1';
    var params = [runDate, userEmail, selectNum];

    var sql2 = 'select runID, runDate, runTime_h, runTime_m, runMain, runSub from tantanDB.addRunTB where runID = ?'

    connection.query(sql, params, function (err, result) {

        if (err) {
            resultCode = 404;
            message = 'select 에러'
            console.log(err);

            res.json({
                'code': resultCode,
                'message': message
            });
        } else {
                var runID = result[0].runID;

                connection.query(sql2, runID, function (err, result) {
            
                    if (err) {
                        resultCode = 404;
                        message = "delete 실패"
                        console.log(err);

                        res.json({
                            'code': resultCode,
                            'message': message
                        });
                    } else {
           
                        resultCode = 200;
                        message = 'select 성공';

                        console.log(result);
                        
                        var runTime_h = result[0].runTime_h;
                        var runTime_m = result[0].runTime_m;
                        var runMain = result[0].runMain;
                        var runSub = result[0].runSub;

                        res.json({
                            'code': resultCode,
                            'message': message,
                            'runID' : runID,
                            'runDate' : runDate,
                            'runTime_h' : runTime_h,
                            'runTime_m' : runTime_m,
                            'runMain' : runMain,
                            'runSub' : runSub
                        });
            
                    }
        
                })
            
        }
    })
});

//목차 - 달력 - 상세보기 - 운동 수정 업데이트 하기
app.post('/user/data/run/modify/update', function (req, res) {

    var selectNum = req.body.selectNum;
    var runTime_h = req.body.runTime_h;
    var runTime_m = req.body.runTime_m;
    var runMain = req.body.runMain;
    var runSub = req.body.runSub;

    var sql = 'UPDATE tantanDB.addRunTB SET runTime_h = ?, runTime_m = ?, runMain = ?, runSub = ? WHERE runID = ?';
    var params = [runTime_h, runTime_m, runMain, runSub, selectNum];

    connection.query(sql, params, function (err, result) {

           
        if (err) {
            resultCode = 404;
            message = "update 실패"
            console.log(err);

            res.json({
                'code': resultCode,
                'message': message
            });
        } else {

            resultCode = 200;
            message = 'update 성공';

            console.log(result);


            res.json({
                'code': resultCode,
                'message': message
            });

        }
        
    })
});

//목차 - 달력 - 상세보기 - 식단 수정 불러오기
app.post('/user/data/meal/modify', function (req, res) {
    var userEmail = req.body.userEmail;
    var mealDate = req.body.selectDate;
    var selectNum = req.body.selectNum;

    var sql = 'SELECT mealID FROM tantanDB.addMealTB where mealDate = ? and userEmail = ? order by mealID limit ? ,1';
    var params = [mealDate, userEmail, selectNum];

    var sql2 = 'SELECT mealDate, mealTime, mealPicture, mealPicturePath, mealMemo FROM tantanDB.addMealTB where mealID = ?'

    connection.query(sql, params, function (err, result) {

        if (err) {
            resultCode = 404;
            message = 'select 에러'
            console.log(err);

            res.json({
                'code': resultCode,
                'message': message
            });
        } else {
                var mealID = result[0].mealID;

                connection.query(sql2, mealID, function (err, result) {
            
                    if (err) {
                        resultCode = 404;
                        message = "select 실패"
                        console.log(err);

                        res.json({
                            'code': resultCode,
                            'message': message
                        });
                    } else {
           
                        resultCode = 200;
                        message = 'select 성공';

                        var mealDate = result[0].mealDate;
                        var mealTime = result[0].mealTime;
                        var mealPicture = result[0].mealPicture;
                        var mealPicturePath = result[0].mealPicturePath;
                        var mealMemo = result[0].mealMemo;

                        res.json({
                            'code': resultCode,
                            'message': message,
                            'mealID' : mealID,
                            'mealDate' : mealDate,
                            'mealTime' : mealTime,
                            'mealPicture' : mealPicture,
                            'mealPicturePath' : mealPicturePath,
                            'mealMemo' : mealMemo
                        });
            
                    }
        
                })
            
        }
    })
});

//목차 - 달력 - 상세보기 - 식단 수정 업데이트 하기
app.post('/user/data/meal/modify/update', function (req, res) {

    var selectNum = req.body.selectNum;
    var mealDate = req.body.mealDate;
    var mealTime = req.body.mealTime;
    var mealPicture = req.body.mealPicture;
    var mealPicturePath = req.body.mealPicturePath;
    var mealMemo = req.body.mealMemo;


    var sql = 'UPDATE tantanDB.addMealTB SET mealDate = ?, mealTime = ?, mealPicture = ?, mealPicturePath = ?, mealMemo = ? WHERE mealID = ?';
    var params = [mealDate, mealTime, mealPicture, mealPicturePath, mealMemo, selectNum];

    connection.query(sql, params, function (err, result) {

        console.log(params)

           
        if (err) {
            resultCode = 404;
            message = "update 실패"
            console.log(message);

            res.json({
                'code': resultCode,
                'message': message
            });
        } else {

            resultCode = 200;
            message = 'update 성공';

            console.log(message);


            res.json({
                'code': resultCode,
                'message': message
            });

        }
        
    })
});


app.get('/user/login', (req, res) => { // getUserData 경로에 GET 요청이 왔을 경우
    console.log(`param userId : ${req.query.id}`)
    connection.query(
      `SELECT * FROM tantanDB.userTB WHERE userId = ${req.query.id};`, // 전달 받은 id(학번) 의 값으로 학생 조회
      (err, rows, fields) => { // SQL 문 Callback 함수
        console.log(rows);
        res.send(rows);
      });
  });

