# ChineseTrainingPlatform

exported at 2024-12-22 18:08:11

## SchoolAdminManagementController

SchoolAdminManagementController


---
### login

> BASIC

**Path:** /api/school-admin/login

**Method:** POST

> REQUEST

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| Content-Type | application/json | YES |  |

**Request Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| account | string | NO |  |  |
| password | string | NO |  |  |

**Request Demo:**

```json
{
  "account": "",
  "password": ""
}
```



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |
| id | integer | NO |  |  |
| token | string | NO |  |  |

**Response Demo:**

```json
{
  "message": "",
  "id": 0,
  "token": ""
}
```




---
### getSchoolAdminInfo

> BASIC

**Path:** /api/school-admin/{id}

**Method:** GET

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| id |  |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |
| data | object | NO |  |  |
| &ensp;&ensp;&#124;─username | string | NO |  |  |
| &ensp;&ensp;&#124;─email | string | NO |  |  |
| &ensp;&ensp;&#124;─name | string | NO |  |  |
| &ensp;&ensp;&#124;─schoolName | string | NO |  |  |
| &ensp;&ensp;&#124;─authorizationCode | string | NO |  |  |
| &ensp;&ensp;&#124;─createDate | string | NO |  |  |

**Response Demo:**

```json
{
  "message": "",
  "data": {
    "username": "",
    "email": "",
    "name": "",
    "schoolName": "",
    "authorizationCode": "",
    "createDate": ""
  }
}
```




---
### changePassword

> BASIC

**Path:** /api/school-admin/{id}/change-password

**Method:** POST

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| id |  |  |

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| Content-Type | application/json | YES |  |

**Request Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| password | string | NO |  |  |
| newPassword | string | NO |  |  |

**Request Demo:**

```json
{
  "password": "",
  "newPassword": ""
}
```



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |

**Response Demo:**

```json
{
  "message": ""
}
```




---
### updateUserName

> BASIC

**Path:** /api/school-admin/{id}/update-username

**Method:** PUT

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| id |  |  |

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| Content-Type | application/json | YES |  |

**Request Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| username | string | NO |  |  |

**Request Demo:**

```json
{
  "username": ""
}
```



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |

**Response Demo:**

```json
{
  "message": ""
}
```




---
### updateName

> BASIC

**Path:** /api/school-admin/{id}/update-name

**Method:** PUT

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| id |  |  |

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| Content-Type | application/json | YES |  |

**Request Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| name | string | NO |  |  |

**Request Demo:**

```json
{
  "name": ""
}
```



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |

**Response Demo:**

```json
{
  "message": ""
}
```




---
### sendVerificationCode

> BASIC

**Path:** /api/school-admin/{id}/send-verification-code

**Method:** POST

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| id |  |  |

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| Content-Type | application/json | YES |  |

**Request Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| email | string | NO |  |  |

**Request Demo:**

```json
{
  "email": ""
}
```



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |
| verificationCode | string | NO |  |  |

**Response Demo:**

```json
{
  "message": "",
  "verificationCode": ""
}
```




---
### bindEmail

> BASIC

**Path:** /api/school-admin/{id}/bind-email

**Method:** POST

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| id |  |  |

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| Content-Type | application/json | YES |  |

**Request Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| email | string | NO |  |  |

**Request Demo:**

```json
{
  "email": ""
}
```



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |

**Response Demo:**

```json
{
  "message": ""
}
```




---
### sendEmailCode

> BASIC

**Path:** /api/school-admin/send-email-code

**Method:** GET

> REQUEST

**Query:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| email |  | YES |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |

**Response Demo:**

```json
{
  "message": ""
}
```




---
### changeEmail

> BASIC

**Path:** /api/school-admin/change-email

**Method:** GET

> REQUEST

**Query:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| newEmail |  | YES |  |
| code |  | YES |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |
| newEmail | string | NO |  |  |

**Response Demo:**

```json
{
  "message": "",
  "newEmail": ""
}
```





## TeacherManagementController

TeacherManagementController


---
### login

> BASIC

**Path:** /api/teacher/login

**Method:** POST

> REQUEST

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| Content-Type | application/json | YES |  |

**Request Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| account | string | NO |  |  |
| password | string | NO |  |  |

**Request Demo:**

```json
{
  "account": "",
  "password": ""
}
```



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |
| id | integer | NO |  |  |
| permission | integer | NO |  |  |
| token | string | NO |  |  |

**Response Demo:**

```json
{
  "message": "",
  "id": 0,
  "permission": 0,
  "token": ""
}
```




---
### sendVerificationCode

> BASIC

**Path:** /api/teacher/send-verification

**Method:** POST

> REQUEST

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| Content-Type | application/json | YES |  |

**Request Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| authorizationCode | string | NO |  |  |
| email | string | NO |  |  |

**Request Demo:**

```json
{
  "authorizationCode": "",
  "email": ""
}
```



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |
| schoolId | integer | NO |  |  |

**Response Demo:**

```json
{
  "message": "",
  "schoolId": 0
}
```




---
### register

> BASIC

**Path:** /api/teacher/register

**Method:** POST

> REQUEST

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| Content-Type | application/json | YES |  |

**Request Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| email | string | NO |  |  |
| verificationCode | string | NO |  |  |
| password | string | NO |  |  |
| confirmPassword | string | NO |  |  |
| schoolId | integer | NO |  |  |

**Request Demo:**

```json
{
  "email": "",
  "verificationCode": "",
  "password": "",
  "confirmPassword": "",
  "schoolId": 0
}
```



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |
| id | integer | NO |  |  |

**Response Demo:**

```json
{
  "message": "",
  "id": 0
}
```




---
### getTeacherInfo

> BASIC

**Path:** /api/teacher/{id}

**Method:** GET

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| id |  |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |
| data | object | NO |  |  |
| &ensp;&ensp;&#124;─name | string | NO |  |  |
| &ensp;&ensp;&#124;─username | string | NO |  |  |
| &ensp;&ensp;&#124;─email | string | NO |  |  |
| &ensp;&ensp;&#124;─phoneNumber | string | NO |  |  |
| &ensp;&ensp;&#124;─schoolName | string | NO |  |  |

**Response Demo:**

```json
{
  "message": "",
  "data": {
    "name": "",
    "username": "",
    "email": "",
    "phoneNumber": "",
    "schoolName": ""
  }
}
```




---
### updateUsername

> BASIC

**Path:** /api/teacher/{id}/update-username

**Method:** POST

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| id |  |  |

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| Content-Type | application/json | YES |  |

**Request Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| username | string | NO |  |  |

**Request Demo:**

```json
{
  "username": ""
}
```



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |

**Response Demo:**

```json
{
  "message": ""
}
```




---
### updatePhoneNumber

> BASIC

**Path:** /api/teacher/{id}/update-phoneNumber

**Method:** POST

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| id |  |  |

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| Content-Type | application/json | YES |  |

**Request Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| phoneNumber | string | NO |  |  |

**Request Demo:**

```json
{
  "phoneNumber": ""
}
```



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |

**Response Demo:**

```json
{
  "message": ""
}
```




---
### updateName

> BASIC

**Path:** /api/teacher/{id}/update-name

**Method:** PUT

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| id |  |  |

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| Content-Type | application/json | YES |  |

**Request Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| name | string | NO |  |  |

**Request Demo:**

```json
{
  "name": ""
}
```



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |

**Response Demo:**

```json
{
  "message": ""
}
```




---
### changePassword

> BASIC

**Path:** /api/teacher/{id}/change-password

**Method:** POST

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| id |  |  |

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| Content-Type | application/json | YES |  |

**Request Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| password | string | NO |  |  |
| newPassword | string | NO |  |  |

**Request Demo:**

```json
{
  "password": "",
  "newPassword": ""
}
```



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |

**Response Demo:**

```json
{
  "message": ""
}
```




---
### sendEmailCode

> BASIC

**Path:** /api/teacher/send-email-code

**Method:** GET

> REQUEST

**Query:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| email |  | YES |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |

**Response Demo:**

```json
{
  "message": ""
}
```




---
### changeEmail

> BASIC

**Path:** /api/teacher/change-email

**Method:** GET

> REQUEST

**Query:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| newEmail |  | YES |  |
| code |  | YES |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |
| newEmail | string | NO |  |  |

**Response Demo:**

```json
{
  "message": "",
  "newEmail": ""
}
```




---
### deleteAccount

> BASIC

**Path:** /api/teacher/delete-account

**Method:** DELETE

> REQUEST



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |

**Response Demo:**

```json
{
  "message": ""
}
```





## StudentController

StudentController


---
### login

> BASIC

**Path:** /api/student/login

**Method:** POST

> REQUEST

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| Content-Type | application/json | YES |  |

**Request Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| account | string | NO |  |  |
| password | string | NO |  |  |

**Request Demo:**

```json
{
  "account": "",
  "password": ""
}
```



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |
| id | integer | NO |  |  |
| token | string | NO |  |  |

**Response Demo:**

```json
{
  "message": "",
  "id": 0,
  "token": ""
}
```




---
### sendVerificationCode

> BASIC

**Path:** /api/student/send-verification

**Method:** POST

> REQUEST

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| Content-Type | application/json | YES |  |

**Request Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| email | string | NO |  |  |

**Request Demo:**

```json
{
  "email": ""
}
```



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |

**Response Demo:**

```json
{
  "message": ""
}
```




---
### studentRegister

> BASIC

**Path:** /api/student/register

**Method:** POST

> REQUEST

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| Content-Type | application/json | YES |  |

**Request Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| email | string | NO |  |  |
| verificationCode | string | NO |  |  |
| username | string | NO |  |  |
| password | string | NO |  |  |
| confirmPassword | string | NO |  |  |

**Request Demo:**

```json
{
  "email": "",
  "verificationCode": "",
  "username": "",
  "password": "",
  "confirmPassword": ""
}
```



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |
| id | integer | NO |  |  |

**Response Demo:**

```json
{
  "message": "",
  "id": 0
}
```




---
### getStudentInfo

> BASIC

**Path:** /api/student/{id}

**Method:** GET

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| id |  |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |
| data | object | NO |  |  |
| &ensp;&ensp;&#124;─username | string | NO |  |  |
| &ensp;&ensp;&#124;─email | string | NO |  |  |
| &ensp;&ensp;&#124;─name | string | NO |  |  |
| &ensp;&ensp;&#124;─grade | integer | NO |  |  |
| &ensp;&ensp;&#124;─schoolName | string | NO |  |  |
| &ensp;&ensp;&#124;─className | string | NO |  |  |

**Response Demo:**

```json
{
  "message": "",
  "data": {
    "username": "",
    "email": "",
    "name": "",
    "grade": 0,
    "schoolName": "",
    "className": ""
  }
}
```




---
### getEssays

> BASIC

**Path:** /api/student/{id}/view-essays

**Method:** GET

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| id |  |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |
| infoData | array | NO |  |  |
| &ensp;&ensp;&#124;─ | object |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─id | integer | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─title | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─date | string | NO |  |  |

**Response Demo:**

```json
{
  "message": "",
  "infoData": [
    {
      "id": 0,
      "title": "",
      "date": ""
    }
  ]
}
```




---
### getEssayInfo

> BASIC

**Path:** /api/student/{id}/essay/get-info/{essayId}

**Method:** GET

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| id |  |  |
| essayId |  |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| inputStreamSource | object | NO |  |  |
| description | string | NO |  |  |
| equality | object | NO |  |  |
| read | boolean | NO |  |  |

**Response Demo:**

```json
{
  "inputStreamSource": {},
  "description": "",
  "equality": {},
  "read": false
}
```





## SystemAdminController

SystemAdminController


---
### login

> BASIC

**Path:** /api/system-admin/login

**Method:** POST

> REQUEST

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| Content-Type | application/json | YES |  |

**Request Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| account | string | NO |  |  |
| password | string | NO |  |  |

**Request Demo:**

```json
{
  "account": "",
  "password": ""
}
```



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |
| id | integer | NO |  |  |
| token | string | NO |  |  |

**Response Demo:**

```json
{
  "message": "",
  "id": 0,
  "token": ""
}
```




---
### getSystemAdminInfo

> BASIC

**Path:** /api/system-admin/{id}

**Method:** GET

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| id |  |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |
| data | object | NO |  |  |
| &ensp;&ensp;&#124;─id | integer | NO |  |  |
| &ensp;&ensp;&#124;─username | string | NO |  |  |
| &ensp;&ensp;&#124;─email | string | NO |  |  |

**Response Demo:**

```json
{
  "message": "",
  "data": {
    "id": 0,
    "username": "",
    "email": ""
  }
}
```




---
### createStandard

> BASIC

**Path:** /api/system-admin/create-course-standard

**Method:** POST

> REQUEST

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| Content-Type | multipart/form-data | YES |  |

**Query:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| executedDate |  | YES |  |

**Form:**

| name | value | required | type | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| file |  | YES | file |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |
| courseStandardId | integer | NO |  |  |

**Response Demo:**

```json
{
  "message": "",
  "courseStandardId": 0
}
```




---
### updateCourseStandard

> BASIC

**Path:** /api/system-admin/update-course-standard/{id}

**Method:** PUT

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| id |  |  |

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| Content-Type | multipart/form-data | YES |  |

**Query:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| executedDate |  | YES |  |

**Form:**

| name | value | required | type | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| file |  | YES | file |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |

**Response Demo:**

```json
{
  "message": ""
}
```




---
### getCourseStandard

> BASIC

**Path:** /api/system-admin/query-course-standard/{id}

**Method:** GET

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| id |  |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| inputStreamSource | object | NO |  |  |
| description | string | NO |  |  |
| equality | object | NO |  |  |
| read | boolean | NO |  |  |

**Response Demo:**

```json
{
  "inputStreamSource": {},
  "description": "",
  "equality": {},
  "read": false
}
```




---
### deleteCourseStandard

> BASIC

**Path:** /api/system-admin/delete-course-standard/{id}

**Method:** DELETE

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| id |  |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |

**Response Demo:**

```json
{
  "message": ""
}
```




---
### getAllCourseStandard

> BASIC

**Path:** /api/system-admin/get-all-course-standards

**Method:** GET

> REQUEST



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |
| courseStandardInfos | array | NO |  |  |
| &ensp;&ensp;&#124;─ | object |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─id | integer | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─title | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─executedDate | string | NO |  |  |

**Response Demo:**

```json
{
  "message": "",
  "courseStandardInfos": [
    {
      "id": 0,
      "title": "",
      "executedDate": ""
    }
  ]
}
```




---
### createKnowledgePoint

> BASIC

**Path:** /api/system-admin/create-knowledge-point

**Method:** POST

> REQUEST

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| Content-Type | application/json | YES |  |

**Request Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| name | string | NO |  |  |
| description | string | NO |  |  |
| type | string | NO |  |  |

**Request Demo:**

```json
{
  "name": "",
  "description": "",
  "type": ""
}
```



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |
| knowledgePointId | integer | NO |  |  |
| type | string | NO |  |  |

**Response Demo:**

```json
{
  "message": "",
  "knowledgePointId": 0,
  "type": ""
}
```




---
### deleteKnowledgePoint

> BASIC

**Path:** /api/system-admin/delete-knowledge-point/{id}

**Method:** DELETE

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| id |  |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |

**Response Demo:**

```json
{
  "message": ""
}
```




---
### updateKnowledgePoint

> BASIC

**Path:** /api/system-admin/update-knowledge-point/{id}

**Method:** PUT

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| id |  |  |

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| Content-Type | application/json | YES |  |

**Request Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| name | string | NO |  |  |
| description | string | NO |  |  |
| type | string | NO |  |  |

**Request Demo:**

```json
{
  "name": "",
  "description": "",
  "type": ""
}
```



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |

**Response Demo:**

```json
{
  "message": ""
}
```




---
### queryKnowledgePoint

> BASIC

**Path:** /api/system-admin/query-knowledge-point/{id}

**Method:** GET

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| id |  |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |
| data | object | NO |  |  |
| &ensp;&ensp;&#124;─name | string | NO |  |  |
| &ensp;&ensp;&#124;─type | string | NO |  |  |
| &ensp;&ensp;&#124;─description | string | NO |  |  |

**Response Demo:**

```json
{
  "message": "",
  "data": {
    "name": "",
    "type": "",
    "description": ""
  }
}
```




---
### getAllKnowledgePoints

> BASIC

**Path:** /api/system-admin/get-all-knowledge-points

**Method:** GET

> REQUEST



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |
| knowledgePointInfos | array | NO |  |  |
| &ensp;&ensp;&#124;─ | object |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─id | integer | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─name | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─description | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─type | string | NO |  |  |

**Response Demo:**

```json
{
  "message": "",
  "knowledgePointInfos": [
    {
      "id": 0,
      "name": "",
      "description": "",
      "type": ""
    }
  ]
}
```




---
### changePassword

> BASIC

**Path:** /api/system-admin/{id}/change-password

**Method:** POST

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| id |  |  |

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| Content-Type | application/json | YES |  |

**Request Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| password | string | NO |  |  |
| newPassword | string | NO |  |  |

**Request Demo:**

```json
{
  "password": "",
  "newPassword": ""
}
```



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |

**Response Demo:**

```json
{
  "message": ""
}
```




---
### sendEmailCode

> BASIC

**Path:** /api/system-admin/send-email-code

**Method:** GET

> REQUEST

**Query:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| email |  | YES |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |

**Response Demo:**

```json
{
  "message": ""
}
```




---
### changeEmail

> BASIC

**Path:** /api/system-admin/change-email

**Method:** GET

> REQUEST

**Query:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| newEmail |  | YES |  |
| code |  | YES |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |
| newEmail | string | NO |  |  |

**Response Demo:**

```json
{
  "message": "",
  "newEmail": ""
}
```





## StudentInfoController

StudentInfoController


---
### studentEditInformation

> BASIC

**Path:** /api/student/{id}/edit-information

**Method:** POST

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| id |  |  |

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| Content-Type | application/json | YES |  |

**Request Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| username | string | NO |  |  |
| name | string | NO |  |  |
| grade | integer | NO |  |  |

**Request Demo:**

```json
{
  "username": "",
  "name": "",
  "grade": 0
}
```



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |
| data | object | NO |  |  |
| &ensp;&ensp;&#124;─username | string | NO |  |  |
| &ensp;&ensp;&#124;─name | string | NO |  |  |
| &ensp;&ensp;&#124;─grade | integer | NO |  |  |

**Response Demo:**

```json
{
  "message": "",
  "data": {
    "username": "",
    "name": "",
    "grade": 0
  }
}
```




---
### studentChangeEmailVerification

> BASIC

**Path:** /api/student/{id}/change-email/send-code

**Method:** POST

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| id |  |  |

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| Content-Type | application/json | YES |  |

**Request Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| email | string | NO |  |  |

**Request Demo:**

```json
{
  "email": ""
}
```



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |

**Response Demo:**

```json
{
  "message": ""
}
```




---
### studentChangeEmail

> BASIC

**Path:** /api/student/{id}/change-email

**Method:** POST

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| id |  |  |

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| Content-Type | application/json | YES |  |

**Request Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| email | string | NO |  |  |
| verificationCode | string | NO |  |  |

**Request Demo:**

```json
{
  "email": "",
  "verificationCode": ""
}
```



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |
| data | object | NO |  |  |
| &ensp;&ensp;&#124;─email | string | NO |  |  |

**Response Demo:**

```json
{
  "message": "",
  "data": {
    "email": ""
  }
}
```




---
### studentChangePassword

> BASIC

**Path:** /api/student/{id}/change-password

**Method:** POST

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| id |  |  |

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| Content-Type | application/json | YES |  |

**Request Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| oldPassword | string | NO |  |  |
| newPassword | string | NO |  |  |

**Request Demo:**

```json
{
  "oldPassword": "",
  "newPassword": ""
}
```



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |

**Response Demo:**

```json
{
  "message": ""
}
```




---
### studentAccountDeactivation

> BASIC

**Path:** /api/student/{id}/account-deactivation

**Method:** DELETE

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| id |  |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |

**Response Demo:**

```json
{
  "message": ""
}
```




---
### studentJoinClass

> BASIC

**Path:** /api/student/{id}/join-class

**Method:** POST

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| id |  |  |

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| Content-Type | application/json | YES |  |

**Request Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| inviteCode | string | NO |  |  |

**Request Demo:**

```json
{
  "inviteCode": ""
}
```



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |
| className | string | NO |  |  |
| schoolName | string | NO |  |  |

**Response Demo:**

```json
{
  "message": "",
  "className": "",
  "schoolName": ""
}
```





## SchoolAdminBusinessController

SchoolAdminBusinessController


---
### generateAuthorizationCode

> BASIC

**Path:** /api/school-admin/{id}/generate-authorization-code

**Method:** GET

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| id |  |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |
| code | string | NO |  |  |
| createDate | string | NO |  |  |

**Response Demo:**

```json
{
  "message": "",
  "code": "",
  "createDate": ""
}
```




---
### deleteTeacher

> BASIC

**Path:** /api/school-admin/{id}/delete-teacher/{teacherId}

**Method:** DELETE

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| id |  |  |
| teacherId |  |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |

**Response Demo:**

```json
{
  "message": ""
}
```




---
### deleteStudent

> BASIC

**Path:** /api/school-admin/{id}/delete-student/{studentId}

**Method:** DELETE

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| id |  |  |
| studentId |  |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |

**Response Demo:**

```json
{
  "message": ""
}
```




---
### queryAllStudents

> BASIC

**Path:** /api/school-admin/{id}/query-all-students

**Method:** GET

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| id |  |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |
| data | array | NO |  |  |
| &ensp;&ensp;&#124;─ | object |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─id | integer | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─name | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─username | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─email | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─grade | integer | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─schoolId | integer | NO |  |  |

**Response Demo:**

```json
{
  "message": "",
  "data": [
    {
      "id": 0,
      "name": "",
      "username": "",
      "email": "",
      "grade": 0,
      "schoolId": 0
    }
  ]
}
```




---
### queryAllTeachers

> BASIC

**Path:** /api/school-admin/{id}/query-all-teachers

**Method:** GET

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| id |  |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |
| data | array | NO |  |  |
| &ensp;&ensp;&#124;─ | object |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─username | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─id | integer | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─name | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─email | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─phoneNumber | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─permission | integer | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─schoolId | integer | NO |  |  |

**Response Demo:**

```json
{
  "message": "",
  "data": [
    {
      "username": "",
      "id": 0,
      "name": "",
      "email": "",
      "phoneNumber": "",
      "permission": 0,
      "schoolId": 0
    }
  ]
}
```




---
### queryStudent

> BASIC

**Path:** /api/school-admin/{id}/query-student/{studentId}

**Method:** GET

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| id |  |  |
| studentId |  |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |
| data | object | NO |  |  |
| &ensp;&ensp;&#124;─id | integer | NO |  |  |
| &ensp;&ensp;&#124;─name | string | NO |  |  |
| &ensp;&ensp;&#124;─username | string | NO |  |  |
| &ensp;&ensp;&#124;─email | string | NO |  |  |
| &ensp;&ensp;&#124;─grade | integer | NO |  |  |
| &ensp;&ensp;&#124;─schoolId | integer | NO |  |  |

**Response Demo:**

```json
{
  "message": "",
  "data": {
    "id": 0,
    "name": "",
    "username": "",
    "email": "",
    "grade": 0,
    "schoolId": 0
  }
}
```




---
### queryTeacher

> BASIC

**Path:** /api/school-admin/{id}/query-teacher/{teacherId}

**Method:** GET

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| id |  |  |
| teacherId |  |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |
| data | object | NO |  |  |
| &ensp;&ensp;&#124;─id | integer | NO |  |  |
| &ensp;&ensp;&#124;─name | string | NO |  |  |
| &ensp;&ensp;&#124;─email | string | NO |  |  |
| &ensp;&ensp;&#124;─phoneNumber | string | NO |  |  |
| &ensp;&ensp;&#124;─schoolId | integer | NO |  |  |

**Response Demo:**

```json
{
  "message": "",
  "data": {
    "id": 0,
    "name": "",
    "email": "",
    "phoneNumber": "",
    "schoolId": 0
  }
}
```




---
### getClasses

> BASIC

**Path:** /api/school-admin/{id}/get-classes

**Method:** GET

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| id |  |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |
| data | array | NO |  |  |
| &ensp;&ensp;&#124;─ | object |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─classId | integer | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─name | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─teacherName | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─inviteCode | string | NO |  |  |

**Response Demo:**

```json
{
  "message": "",
  "data": [
    {
      "classId": 0,
      "name": "",
      "teacherName": "",
      "inviteCode": ""
    }
  ]
}
```




---
### queryClass

> BASIC

**Path:** /api/school-admin/{id}/query-class

**Method:** GET

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| id |  |  |

**Query:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| classId |  | YES |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |
| data | object | NO |  |  |
| &ensp;&ensp;&#124;─className | string | NO |  |  |
| &ensp;&ensp;&#124;─classDescription | string | NO |  |  |
| &ensp;&ensp;&#124;─groups | array | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─ | object |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─groupId | integer | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─groupName | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─groupDescription | string | NO |  |  |
| &ensp;&ensp;&#124;─students | array | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─ | object |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─studentId | integer | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─studentName | string | NO |  |  |

**Response Demo:**

```json
{
  "message": "",
  "data": {
    "className": "",
    "classDescription": "",
    "groups": [
      {
        "groupId": 0,
        "groupName": "",
        "groupDescription": ""
      }
    ],
    "students": [
      {
        "studentId": 0,
        "studentName": ""
      }
    ]
  }
}
```




---
### getClassKnowledgePointStatus

> BASIC

**Path:** /api/school-admin/class/knowledge-point-status

**Method:** GET

> REQUEST

**Query:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| classId |  | YES |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |
| data | array | NO |  |  |
| &ensp;&ensp;&#124;─ | object |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─name | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─score | number | NO |  |  |

**Response Demo:**

```json
{
  "message": "",
  "data": [
    {
      "name": "",
      "score": 0.0
    }
  ]
}
```




---
### getTeachersToChange

> BASIC

**Path:** /api/school-admin/get-teachers-to-change

**Method:** GET

> REQUEST



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |
| data | array | NO |  |  |
| &ensp;&ensp;&#124;─ | object |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─teacherId | integer | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─teacherName | string | NO |  |  |

**Response Demo:**

```json
{
  "message": "",
  "data": [
    {
      "teacherId": 0,
      "teacherName": ""
    }
  ]
}
```




---
### changeTeacherOfClass

> BASIC

**Path:** /api/school-admin/change-teacher-of-class

**Method:** GET

> REQUEST

**Query:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| classId |  | YES |  |
| teacherId |  | YES |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |

**Response Demo:**

```json
{
  "message": ""
}
```




---
### levelUpTeacher

> BASIC

**Path:** /api/school-admin/level-up

**Method:** PUT

> REQUEST

**Query:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| id |  | YES |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |

**Response Demo:**

```json
{
  "message": ""
}
```




---
### levelDownTeacher

> BASIC

**Path:** /api/school-admin/level-down

**Method:** PUT

> REQUEST

**Query:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| id |  | YES |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |

**Response Demo:**

```json
{
  "message": ""
}
```





## TeacherBusinessController

TeacherBusinessController


---
### getCourseStandard

> BASIC

**Path:** /api/teacher/query-course-standard/{id}

**Method:** GET

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| id |  |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| inputStreamSource | object | NO |  |  |
| description | string | NO |  |  |
| equality | object | NO |  |  |
| read | boolean | NO |  |  |

**Response Demo:**

```json
{
  "inputStreamSource": {},
  "description": "",
  "equality": {},
  "read": false
}
```




---
### getAllCourseStandard

> BASIC

**Path:** /api/teacher/get-all-course-standards

**Method:** GET

> REQUEST



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |
| courseStandardInfos | array | NO |  |  |
| &ensp;&ensp;&#124;─ | object |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─id | integer | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─title | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─executedDate | string | NO |  |  |

**Response Demo:**

```json
{
  "message": "",
  "courseStandardInfos": [
    {
      "id": 0,
      "title": "",
      "executedDate": ""
    }
  ]
}
```




---
### createClass

> BASIC

**Path:** /api/teacher/{id}/create-class

**Method:** POST

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| id |  |  |

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| Content-Type | application/json | YES |  |

**Request Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| className | string | NO |  |  |
| classDescription | string | NO |  |  |

**Request Demo:**

```json
{
  "className": "",
  "classDescription": ""
}
```



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |
| classCode | string | NO |  |  |
| classId | integer | NO |  |  |

**Response Demo:**

```json
{
  "message": "",
  "classCode": "",
  "classId": 0
}
```




---
### getClasses

> BASIC

**Path:** /api/teacher/{id}/get-classes

**Method:** GET

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| id |  |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |
| data | array | NO |  |  |
| &ensp;&ensp;&#124;─ | object |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─classId | integer | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─className | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─classDescription | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─classCode | string | NO |  |  |

**Response Demo:**

```json
{
  "message": "",
  "data": [
    {
      "classId": 0,
      "className": "",
      "classDescription": "",
      "classCode": ""
    }
  ]
}
```




---
### getGroups

> BASIC

**Path:** /api/teacher/{id}/get-groups

**Method:** GET

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| id |  |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  | 响应消息 |
| data | array | NO |  | 小组信息列表 |
| &ensp;&ensp;&#124;─ | object |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─groupId | integer | NO |  | 小组ID |
| &ensp;&ensp;&ensp;&ensp;&#124;─groupName | string | NO |  | 小组名称 |
| &ensp;&ensp;&ensp;&ensp;&#124;─className | string | NO |  | 所属班级名称 (可选) |
| &ensp;&ensp;&ensp;&ensp;&#124;─groupDescription | string | NO |  | 小组描述 |

**Response Demo:**

```json
{
  "message": "",
  "data": [
    {
      "groupId": 0,
      "groupName": "",
      "className": "",
      "groupDescription": ""
    }
  ]
}
```




---
### createGroup

> BASIC

**Path:** /api/teacher/{id}/create-group

**Method:** POST

> REQUEST

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| Content-Type | application/json | YES |  |

**Request Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| classId | integer | NO |  | 所属班级的唯一标识符 |
| groupName | string | NO |  | 小组名称 |
| studentIds | array | NO |  | 学生ID的列表 |
| &ensp;&ensp;&#124;─ | integer |  |  |  |
| groupDescription | string | NO |  | 小组描述 |

**Request Demo:**

```json
{
  "classId": 0,
  "groupName": "",
  "studentIds": [
    0
  ],
  "groupDescription": ""
}
```



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |
| groupId | integer | NO |  |  |

**Response Demo:**

```json
{
  "message": "",
  "groupId": 0
}
```




---
### getClassMembers

> BASIC

**Path:** /api/teacher/{id}/get-class-members

**Method:** GET

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| id |  |  |

**Query:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| classId |  | YES |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |
| data | array | NO |  |  |
| &ensp;&ensp;&#124;─ | object |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─studentId | integer | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─studentName | string | NO |  |  |

**Response Demo:**

```json
{
  "message": "",
  "data": [
    {
      "studentId": 0,
      "studentName": ""
    }
  ]
}
```




---
### disbandClass

> BASIC

**Path:** /api/teacher/{teacherId}/classes/disband

**Method:** DELETE

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| teacherId |  |  |

**Query:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| classId |  | YES |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |

**Response Demo:**

```json
{
  "message": ""
}
```




---
### disbandGroup

> BASIC

**Path:** /api/teacher/{teacherId}/disband-group

**Method:** DELETE

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| teacherId |  |  |

**Query:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| groupId |  | YES |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |

**Response Demo:**

```json
{
  "message": ""
}
```




---
### getGroupMembers

> BASIC

**Path:** /api/teacher/{teacherId}/groups-members

**Method:** GET

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| teacherId |  |  |

**Query:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| groupId |  | YES |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |
| students | array | NO |  |  |
| &ensp;&ensp;&#124;─ | object |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─studentId | integer | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─studentName | string | NO |  |  |

**Response Demo:**

```json
{
  "message": "",
  "students": [
    {
      "studentId": 0,
      "studentName": ""
    }
  ]
}
```




---
### updateClass

> BASIC

**Path:** /api/teacher/{id}/update-class

**Method:** PUT

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| id |  |  |

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| Content-Type | application/json | YES |  |

**Request Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| classId | integer | NO |  |  |
| className | string | NO |  |  |
| classDescription | string | NO |  |  |

**Request Demo:**

```json
{
  "classId": 0,
  "className": "",
  "classDescription": ""
}
```



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |

**Response Demo:**

```json
{
  "message": ""
}
```




---
### viewKnowledgePoints

> BASIC

**Path:** /api/teacher/{id}/view-knowledge-point

**Method:** GET

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| id |  |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |
| knowledgePoints | object | NO |  |  |
| &ensp;&ensp;&#124;─key | array |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─ | object |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─name | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─type | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─description | string | NO |  |  |

**Response Demo:**

```json
{
  "message": "",
  "knowledgePoints": {
    "": [
      {
        "name": "",
        "type": "",
        "description": ""
      }
    ]
  }
}
```




---
### getKnowledgePoint

> BASIC

**Path:** /api/teacher/{id}/list-knowledge-point

**Method:** GET

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| id |  |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |
| knowledgePoints | object | NO |  |  |
| &ensp;&ensp;&#124;─key | array |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─ | object |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─id | integer | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─name | string | NO |  |  |

**Response Demo:**

```json
{
  "message": "",
  "knowledgePoints": {
    "": [
      {
        "id": 0,
        "name": ""
      }
    ]
  }
}
```




---
### uploadQuestion

> BASIC

**Path:** /api/teacher/{id}/upload-question

**Method:** POST

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| id |  |  |

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| Content-Type | application/json | YES |  |

**Request Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| questionType | string | NO |  | '单题', '文言文阅读', '记叙文阅读', '非连续性文本阅读', '古诗词曲鉴赏', '名著阅读' |
| questions | array | NO |  |  |
| &ensp;&ensp;&#124;─ | object |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─type | string | NO |  | CHOICE, FILL_IN_BLANK, SHORT_ANSWER, ESSAY |
| &ensp;&ensp;&ensp;&ensp;&#124;─problem | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─choices | array | NO |  | 若不是选择题则为空 |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─ | string |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─answer | array | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─ | string |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─analysis | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─knowledgePointId | integer | NO |  |  |
| body | string | NO |  |  |

**Request Demo:**

```json
{
  "questionType": "",
  "questions": [
    {
      "type": "",
      "problem": "",
      "choices": [
        ""
      ],
      "answer": [
        ""
      ],
      "analysis": "",
      "knowledgePointId": 0
    }
  ],
  "body": ""
}
```



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |

**Response Demo:**

```json
{
  "message": ""
}
```




---
### getApplications

> BASIC

**Path:** /api/teacher/{id}/get-applications

**Method:** GET

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| id |  |  |

**Query:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| classId |  | YES |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |
| data | array | NO |  |  |
| &ensp;&ensp;&#124;─ | object |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─joinClassId | integer | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─studentId | integer | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─userName | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─name | string | NO |  |  |

**Response Demo:**

```json
{
  "message": "",
  "data": [
    {
      "joinClassId": 0,
      "studentId": 0,
      "userName": "",
      "name": ""
    }
  ]
}
```




---
### allowApplication

> BASIC

**Path:** /api/teacher/{id}/allow-application

**Method:** GET

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| id |  |  |

**Query:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| joinClassId |  | YES |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |

**Response Demo:**

```json
{
  "message": ""
}
```




---
### removeStudentFromClass

> BASIC

**Path:** /api/teacher/{teacherId}/classes/remove-student

**Method:** DELETE

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| teacherId |  |  |

**Query:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| studentId |  | YES |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |

**Response Demo:**

```json
{
  "message": ""
}
```




---
### removeStudentFromGroup

> BASIC

**Path:** /api/teacher/{id}/group/remove-student

**Method:** DELETE

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| id |  |  |

**Query:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| groupId |  | YES |  |
| studentId |  | YES |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |

**Response Demo:**

```json
{
  "message": ""
}
```




---
### getAllWaitingQuestions

> BASIC

**Path:** /api/teacher/get-all-waiting-questions

**Method:** GET

> REQUEST



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |
| questions | array | NO |  |  |
| &ensp;&ensp;&#124;─ | object |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─id | integer | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─questionId | integer | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─type | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─uploadTime | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─uploadTeacher | string | NO |  |  |

**Response Demo:**

```json
{
  "message": "",
  "questions": [
    {
      "id": 0,
      "questionId": 0,
      "type": "",
      "uploadTime": "",
      "uploadTeacher": ""
    }
  ]
}
```




---
### getALLAccessQuestions

> BASIC

**Path:** /api/teacher/get-all-access-questions

**Method:** GET

> REQUEST



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |
| questions | array | NO |  |  |
| &ensp;&ensp;&#124;─ | object |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─id | integer | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─questionId | integer | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─type | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─uploadTime | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─uploadTeacher | string | NO |  |  |

**Response Demo:**

```json
{
  "message": "",
  "questions": [
    {
      "id": 0,
      "questionId": 0,
      "type": "",
      "uploadTime": "",
      "uploadTeacher": ""
    }
  ]
}
```




---
### getQuestion

> BASIC

**Path:** /api/teacher/get-question

**Method:** GET

> REQUEST

**Query:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| questionId |  | YES |  |
| type |  | YES |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |
| body | string | NO |  |  |
| content | string | NO |  | 以下部分 为小题时的response |
| answer | string | NO |  |  |
| explanation | string | NO |  |  |
| options | array | NO |  |  |
| &ensp;&ensp;&#124;─ | string |  |  |  |
| type | string | NO |  |  |
| knowledgePoint | string | NO |  |  |
| subQuestions | array | NO |  | 以下部分 为大题时的response |
| &ensp;&ensp;&#124;─ | object |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─content | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─answer | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─explanation | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─options | array | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─ | string |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─type | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─knowledgePoint | string | NO |  |  |

**Response Demo:**

```json
{
  "message": "",
  "body": "",
  "content": "",
  "answer": "",
  "explanation": "",
  "options": [
    ""
  ],
  "type": "",
  "knowledgePoint": "",
  "subQuestions": [
    {
      "content": "",
      "answer": "",
      "explanation": "",
      "options": [
        ""
      ],
      "type": "",
      "knowledgePoint": ""
    }
  ]
}
```




---
### deleteQuestion

> BASIC

**Path:** /api/teacher/delete-question

**Method:** DELETE

> REQUEST

**Query:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| questionId |  | YES |  |
| type |  | YES |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |

**Response Demo:**

```json
{
  "message": ""
}
```




---
### getAvgScore

> BASIC

**Path:** /api/teacher/{id}/get-student-situation

**Method:** GET

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| id |  |  |

**Query:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| studentId |  | YES |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |
| data | object | NO |  |  |
| &ensp;&ensp;&#124;─averageHomeworkScore | number | NO |  |  |
| &ensp;&ensp;&#124;─classRank | integer | NO |  |  |

**Response Demo:**

```json
{
  "message": "",
  "data": {
    "averageHomeworkScore": 0.0,
    "classRank": 0
  }
}
```




---
### getMultidimensionalScores

> BASIC

**Path:** /api/teacher/{id}/get-student-multidimensional-scores

**Method:** GET

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| id |  |  |

**Query:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| studentId |  | YES |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |
| data | array | NO |  |  |
| &ensp;&ensp;&#124;─ | object |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─name | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─score | number | NO |  |  |

**Response Demo:**

```json
{
  "message": "",
  "data": [
    {
      "name": "",
      "score": 0.0
    }
  ]
}
```




---
### getWeaknessScores

> BASIC

**Path:** /api/teacher/{id}/get-student-weakness-scores

**Method:** GET

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| id |  |  |

**Query:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| studentId |  | YES |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |
| data | array | NO |  |  |
| &ensp;&ensp;&#124;─ | object |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─type | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─weaknessName | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─weaknessScore | number | NO |  |  |

**Response Demo:**

```json
{
  "message": "",
  "data": [
    {
      "type": "",
      "weaknessName": "",
      "weaknessScore": 0.0
    }
  ]
}
```




---
### getHistoryScores

> BASIC

**Path:** /api/teacher/{id}/get-student-historical-homework-scores

**Method:** GET

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| id |  |  |

**Query:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| studentId |  | YES |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |
| data | array | NO |  |  |
| &ensp;&ensp;&#124;─ | object |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─date | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─score | integer | NO |  |  |

**Response Demo:**

```json
{
  "message": "",
  "data": [
    {
      "date": "",
      "score": 0
    }
  ]
}
```




---
### searchQuestions

> BASIC

**Path:** /api/teacher/search-questions

**Method:** POST

> REQUEST

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| Content-Type | application/json | YES |  |

**Request Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| type | string | NO |  | "CHOICE" or "FILL_IN_BLANK", "SHORT_ANSWER", "ESSAY" |
| knowledgeType | string | NO |  |  |
| knowledgeId | integer | NO |  |  |
| difficulty | string | NO |  | "容易" or "普通" or "困难" or '全部' |
| mode | string | NO |  | "latest" or "mostUsed" or '' |
| sortOrder | string | NO |  |  |
| page | integer | NO |  |  |
| pageSize | integer | NO |  |  |
| search | string | NO |  |  |

**Request Demo:**

```json
{
  "type": "",
  "knowledgeType": "",
  "knowledgeId": 0,
  "difficulty": "",
  "mode": "",
  "sortOrder": "",
  "page": 0,
  "pageSize": 0,
  "search": ""
}
```



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| totalCount | integer | NO |  |  |
| pageSize | integer | NO |  |  |
| currentPage | integer | NO |  |  |
| totalPages | integer | NO |  |  |
| bigQuestions | array | NO | new ArrayList<>() |  |
| &ensp;&ensp;&#124;─ | object |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─bodyId | integer | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─body | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─subQuestion | array | NO | new ArrayList<>() |  |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─ | object |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─question | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─answer | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─explanation | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─options | array | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─ | string |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─type | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─knowledge | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─difficulty | number | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─referencedCount | integer | NO |  |  |
| questions | array | NO | new ArrayList<>() |  |
| &ensp;&ensp;&#124;─ | object |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─questionId | integer | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─body | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─question | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─answer | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─options | array | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─ | string |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─explanation | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─referencedCount | integer | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─difficulty | number | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─type | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─knowledge | string | NO |  |  |

**Response Demo:**

```json
{
  "totalCount": 0,
  "pageSize": 0,
  "currentPage": 0,
  "totalPages": 0,
  "bigQuestions": [
    {
      "bodyId": 0,
      "body": "",
      "subQuestion": [
        {
          "question": "",
          "answer": "",
          "explanation": "",
          "options": [
            ""
          ],
          "type": "",
          "knowledge": ""
        }
      ],
      "difficulty": 0.0,
      "referencedCount": 0
    }
  ],
  "questions": [
    {
      "questionId": 0,
      "body": "",
      "question": "",
      "answer": "",
      "options": [
        ""
      ],
      "explanation": "",
      "referencedCount": 0,
      "difficulty": 0.0,
      "type": "",
      "knowledge": ""
    }
  ]
}
```




---
### generatePaper

> BASIC

**Path:** /api/teacher/generate-paper

**Method:** POST

> REQUEST

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| Content-Type | application/json | YES |  |

**Request Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| name | string | NO |  |  |
| creatorId | integer | NO |  |  |
| totalScore | integer | NO |  |  |
| difficulty | number | NO |  |  |
| questions | array | NO |  |  |
| &ensp;&ensp;&#124;─ | object |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─id | integer | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─type | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─score | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─sequence | integer | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─subScores | array | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─ | string |  |  |  |

**Request Demo:**

```json
{
  "name": "",
  "creatorId": 0,
  "totalScore": 0,
  "difficulty": 0.0,
  "questions": [
    {
      "id": 0,
      "type": "",
      "score": "",
      "sequence": 0,
      "subScores": [
        ""
      ]
    }
  ]
}
```



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |

**Response Demo:**

```json
{
  "message": ""
}
```




---
### getPapers

> BASIC

**Path:** /api/teacher/papers/{id}

**Method:** GET

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| id |  |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |
| papers | array | NO |  |  |
| &ensp;&ensp;&#124;─ | object |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─id | integer | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─name | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─totalScore | integer | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─createTime | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─difficulty | number | NO |  |  |

**Response Demo:**

```json
{
  "message": "",
  "papers": [
    {
      "id": 0,
      "name": "",
      "totalScore": 0,
      "createTime": "",
      "difficulty": 0.0
    }
  ]
}
```




---
### getPaper

> BASIC

**Path:** /api/teacher/paper

**Method:** GET

> REQUEST

**Query:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| id |  | YES |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |
| totalScore | integer | NO |  |  |
| questions | array | NO |  |  |
| &ensp;&ensp;&#124;─ | object |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─body | string | NO |  | 题干 |
| &ensp;&ensp;&ensp;&ensp;&#124;─sequence | integer | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─score | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─question | string | NO |  | 小题时不为空 |
| &ensp;&ensp;&ensp;&ensp;&#124;─answer | string | NO |  | 小题时不为空 |
| &ensp;&ensp;&ensp;&ensp;&#124;─explanation | string | NO |  | 小题时不为空 |
| &ensp;&ensp;&ensp;&ensp;&#124;─options | array | NO |  | 小题且为选择时不为空 |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─ | string |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─type | string | NO |  | 题目类型 |
| &ensp;&ensp;&ensp;&ensp;&#124;─knowledge | string | NO |  | 知识点 |
| &ensp;&ensp;&ensp;&ensp;&#124;─subQuestions | array | NO |  | 大题时不为空 |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─ | object |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─question | string | NO |  | 子问题内容 |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─answer | string | NO |  | 子问题答案 |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─explanation | string | NO |  | 子问题解析 |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─options | array | NO |  | 子问题选项（如果是选择题） |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─ | string |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─type | string | NO |  | 子问题类型 |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─knowledge | string | NO |  | 子问题的知识点 |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─score | string | NO |  |  |

**Response Demo:**

```json
{
  "message": "",
  "totalScore": 0,
  "questions": [
    {
      "body": "",
      "sequence": 0,
      "score": "",
      "question": "",
      "answer": "",
      "explanation": "",
      "options": [
        ""
      ],
      "type": "",
      "knowledge": "",
      "subQuestions": [
        {
          "question": "",
          "answer": "",
          "explanation": "",
          "options": [
            ""
          ],
          "type": "",
          "knowledge": "",
          "score": ""
        }
      ]
    }
  ]
}
```




---
### getUploadedQuestions

> BASIC

**Path:** /api/teacher/uploaded-questions

**Method:** GET

> REQUEST



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |
| uploadedQuestions | array | NO |  |  |
| &ensp;&ensp;&#124;─ | object |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─questionId | integer | NO |  | 问题 ID |
| &ensp;&ensp;&ensp;&ensp;&#124;─type | string | NO |  | 题目类型 (small or big) |
| &ensp;&ensp;&ensp;&ensp;&#124;─uploadTime | string | NO |  | 上传时间 (string 格式) |
| &ensp;&ensp;&ensp;&ensp;&#124;─status | string | NO |  | 状态 (未审核, 通过, 拒绝) |
| &ensp;&ensp;&ensp;&ensp;&#124;─comment | string | NO |  | 备注 |
| &ensp;&ensp;&ensp;&ensp;&#124;─executeTeacher | string | NO |  | 审核老师名字 |

**Response Demo:**

```json
{
  "message": "",
  "uploadedQuestions": [
    {
      "questionId": 0,
      "type": "",
      "uploadTime": "",
      "status": "",
      "comment": "",
      "executeTeacher": ""
    }
  ]
}
```




---
### publishHomework

> BASIC

**Path:** /api/teacher/homework/publish

**Method:** POST

> REQUEST

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| Content-Type | application/json | YES |  |

**Request Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| name | string | NO |  |  |
| referencedPaperId | integer | NO |  |  |
| targetIds | array | NO |  |  |
| &ensp;&ensp;&#124;─ | integer |  |  |  |
| targetType | string | NO |  |  |
| publishTime | string | NO |  |  |
| dueTime | string | NO |  |  |
| description | string | NO |  |  |

**Request Demo:**

```json
{
  "name": "",
  "referencedPaperId": 0,
  "targetIds": [
    0
  ],
  "targetType": "",
  "publishTime": "",
  "dueTime": "",
  "description": ""
}
```



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |

**Response Demo:**

```json
{
  "message": ""
}
```




---
### deletePaper

> BASIC

**Path:** /api/teacher/delete-paper/{id}

**Method:** DELETE

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| id |  |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |

**Response Demo:**

```json
{
  "message": ""
}
```




---
### denyUploadQuestion

> BASIC

**Path:** /api/teacher/deny-upload-question

**Method:** PUT

> REQUEST

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| Content-Type | application/json | YES |  |

**Request Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| id | integer | NO |  |  |
| comment | string | NO |  |  |

**Request Demo:**

```json
{
  "id": 0,
  "comment": ""
}
```



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |

**Response Demo:**

```json
{
  "message": ""
}
```




---
### getAssignmentList

> BASIC

**Path:** /api/teacher/{id}/get-assignment-list

**Method:** GET

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| id |  |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |
| data | array | NO |  |  |
| &ensp;&ensp;&#124;─ | object |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─assignmentId | integer | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─assignmentTitle | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─assignmentDescription | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─startTime | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─endTime | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─paperId | integer | NO |  |  |

**Response Demo:**

```json
{
  "message": "",
  "data": [
    {
      "assignmentId": 0,
      "assignmentTitle": "",
      "assignmentDescription": "",
      "startTime": "",
      "endTime": "",
      "paperId": 0
    }
  ]
}
```




---
### getSubmissionList

> BASIC

**Path:** /api/teacher/{id}/get-submission-list

**Method:** GET

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| id |  |  |

**Query:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| assignmentId |  | YES |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |
| assignmentId | integer | NO |  |  |
| data | array | NO |  |  |
| &ensp;&ensp;&#124;─ | object |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─studentId | integer | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─studentName | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─isSubmitted | integer | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─submitTime | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─totalScore | integer | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─isMarked | integer | NO |  |  |

**Response Demo:**

```json
{
  "message": "",
  "assignmentId": 0,
  "data": [
    {
      "studentId": 0,
      "studentName": "",
      "isSubmitted": 0,
      "submitTime": "",
      "totalScore": 0,
      "isMarked": 0
    }
  ]
}
```




---
### 批准题目申请

> BASIC

**Path:** /api/teacher/approve-question

**Method:** PUT

> REQUEST

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| Content-Type | application/json | YES |  |

**Request Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| id | integer | NO |  | upload_question 的 ID |
| body | string | NO |  | 题干内容 |
| comment | string | NO |  | 备注 |
| questions | array | NO |  | 题目列表 |
| &ensp;&ensp;&#124;─ | object |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─problem | string | NO |  | 问题描述 |
| &ensp;&ensp;&ensp;&ensp;&#124;─choices | array | NO |  | 选项列表，如果是选择题则提供选项，非选择题为空数组 |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─ | string |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─answer | string | NO |  | 题目答案 |
| &ensp;&ensp;&ensp;&ensp;&#124;─analysis | string | NO |  | 题目解析 |

**Request Demo:**

```json
{
  "id": 0,
  "body": "",
  "comment": "",
  "questions": [
    {
      "problem": "",
      "choices": [
        ""
      ],
      "answer": "",
      "analysis": ""
    }
  ]
}
```



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |

**Response Demo:**

```json
{
  "message": ""
}
```




---
### modifyQuestion

> BASIC

**Path:** /api/teacher/modify-question

**Method:** PUT

> REQUEST

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| Content-Type | application/json | YES |  |

**Request Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| id | integer | NO |  | upload_question 的 ID |
| body | string | NO |  | 题干内容 |
| questions | array | NO |  | 题目列表 |
| &ensp;&ensp;&#124;─ | object |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─problem | string | NO |  | 问题描述 |
| &ensp;&ensp;&ensp;&ensp;&#124;─choices | array | NO |  | 选项列表，如果是选择题则提供选项，非选择题为空数组 |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─ | string |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─answer | string | NO |  | 题目答案 |
| &ensp;&ensp;&ensp;&ensp;&#124;─analysis | string | NO |  | 题目解析 |

**Request Demo:**

```json
{
  "id": 0,
  "body": "",
  "questions": [
    {
      "problem": "",
      "choices": [
        ""
      ],
      "answer": "",
      "analysis": ""
    }
  ]
}
```



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |

**Response Demo:**

```json
{
  "message": ""
}
```




---
### getSubmission

> BASIC

**Path:** /api/teacher/{id}/get-submission

**Method:** GET

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| id |  |  |

**Query:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| assignmentId |  | YES |  |
| studentId |  | YES |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |
| totalScore | integer | NO |  |  |
| questions | array | NO |  |  |
| &ensp;&ensp;&#124;─ | object |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─submissionAnswerId | integer | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─body | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─sequence | integer | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─score | integer | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─subQuestions | array | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─ | object |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─submissionAnswerId | integer | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─question | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─answer | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─explanation | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─options | array | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─ | string |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─type | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─subScore | integer | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─studentAnswer | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─question | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─answer | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─explanation | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─options | array | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─ | string |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─type | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─studentAnswer | string | NO |  |  |

**Response Demo:**

```json
{
  "message": "",
  "totalScore": 0,
  "questions": [
    {
      "submissionAnswerId": 0,
      "body": "",
      "sequence": 0,
      "score": 0,
      "subQuestions": [
        {
          "submissionAnswerId": 0,
          "question": "",
          "answer": "",
          "explanation": "",
          "options": [
            ""
          ],
          "type": "",
          "subScore": 0,
          "studentAnswer": ""
        }
      ],
      "question": "",
      "answer": "",
      "explanation": "",
      "options": [
        ""
      ],
      "type": "",
      "studentAnswer": ""
    }
  ]
}
```




---
### generatePaperWithTypes

> BASIC

**Path:** /api/teacher/generate-paper-with-types

**Method:** POST

> REQUEST

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| Content-Type | application/json | YES |  |

**Request Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| types | array | NO |  |  |
| &ensp;&ensp;&#124;─ | object |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─type | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─number | integer | NO |  |  |

**Request Demo:**

```json
{
  "types": [
    {
      "type": "",
      "number": 0
    }
  ]
}
```



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |
| questions | array | NO |  |  |
| &ensp;&ensp;&#124;─ | object |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─id | integer | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─body | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─subQuestions | array | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─ | object |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─content | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─answer | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─explanation | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─type | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─knowledgePoint | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─difficulty | number | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─options | array | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─ | string |  |  |  |
| essays | array | NO |  |  |
| &ensp;&ensp;&#124;─ | object |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─id | integer | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─content | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─explanation | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─type | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─difficulty | number | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─knowledgePoint | string | NO |  |  |

**Response Demo:**

```json
{
  "message": "",
  "questions": [
    {
      "id": 0,
      "body": "",
      "subQuestions": [
        {
          "content": "",
          "answer": "",
          "explanation": "",
          "type": "",
          "knowledgePoint": "",
          "difficulty": 0.0,
          "options": [
            ""
          ]
        }
      ]
    }
  ],
  "essays": [
    {
      "id": 0,
      "content": "",
      "explanation": "",
      "type": "",
      "difficulty": 0.0,
      "knowledgePoint": ""
    }
  ]
}
```




---
### autoPaper

> BASIC

**Path:** /api/teacher/paper/auto

**Method:** GET

> REQUEST



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  | 消息 |
| questions | array | NO |  | 普通问题 |
| &ensp;&ensp;&#124;─ | object |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─id | integer | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─body | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─content | string | NO |  | 问题内容 |
| &ensp;&ensp;&ensp;&ensp;&#124;─answer | string | NO |  | 答案 |
| &ensp;&ensp;&ensp;&ensp;&#124;─explanation | string | NO |  | 解释 |
| &ensp;&ensp;&ensp;&ensp;&#124;─options | array | NO |  | 选项 |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─ | string |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─type | string | NO |  | 题目类型 |
| &ensp;&ensp;&ensp;&ensp;&#124;─knowledgePoint | string | NO |  | 知识点 |
| &ensp;&ensp;&ensp;&ensp;&#124;─difficulty | number | NO |  |  |
| bigQuestions | array | NO |  | 后续大题 |
| &ensp;&ensp;&#124;─ | object |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─id | integer | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─body | string | NO |  | 题干内容 |
| &ensp;&ensp;&ensp;&ensp;&#124;─subQuestions | array | NO |  | 子问题列表 |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─ | object |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─content | string | NO |  | 子问题内容 |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─answer | string | NO |  | 答案 |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─explanation | string | NO |  | 解释 |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─options | array | NO |  | 选项 |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─ | string |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─type | string | NO |  | 题目类型 |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─knowledgePoint | string | NO |  | 知识点 |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─difficulty | number | NO |  |  |
| essay | object | NO |  |  |
| &ensp;&ensp;&#124;─id | integer | NO |  |  |
| &ensp;&ensp;&#124;─body | string | NO |  |  |
| &ensp;&ensp;&#124;─content | string | NO |  | 问题内容 |
| &ensp;&ensp;&#124;─answer | string | NO |  | 答案 |
| &ensp;&ensp;&#124;─explanation | string | NO |  | 解释 |
| &ensp;&ensp;&#124;─options | array | NO |  | 选项 |
| &ensp;&ensp;&ensp;&ensp;&#124;─ | string |  |  |  |
| &ensp;&ensp;&#124;─type | string | NO |  | 题目类型 |
| &ensp;&ensp;&#124;─knowledgePoint | string | NO |  | 知识点 |
| &ensp;&ensp;&#124;─difficulty | number | NO |  |  |

**Response Demo:**

```json
{
  "message": "",
  "questions": [
    {
      "id": 0,
      "body": "",
      "content": "",
      "answer": "",
      "explanation": "",
      "options": [
        ""
      ],
      "type": "",
      "knowledgePoint": "",
      "difficulty": 0.0
    }
  ],
  "bigQuestions": [
    {
      "id": 0,
      "body": "",
      "subQuestions": [
        {
          "content": "",
          "answer": "",
          "explanation": "",
          "options": [
            ""
          ],
          "type": "",
          "knowledgePoint": "",
          "difficulty": 0.0
        }
      ]
    }
  ],
  "essay": {
    "id": 0,
    "body": "",
    "content": "",
    "answer": "",
    "explanation": "",
    "options": [
      ""
    ],
    "type": "",
    "knowledgePoint": "",
    "difficulty": 0.0
  }
}
```




---
### markSubmission

> BASIC

**Path:** /api/teacher/mark-submission

**Method:** POST

> REQUEST

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| Content-Type | application/json | YES |  |

**Request Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| data | array | NO |  |  |
| &ensp;&ensp;&#124;─ | object |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─submissionAnswerId | integer | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─markScore | integer | NO |  |  |
| feedback | string | NO |  |  |

**Request Demo:**

```json
{
  "data": [
    {
      "submissionAnswerId": 0,
      "markScore": 0
    }
  ],
  "feedback": ""
}
```



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |

**Response Demo:**

```json
{
  "message": ""
}
```




---
### getClassHistoricalScores

> BASIC

**Path:** /api/teacher/class/historical-scores

**Method:** GET

> REQUEST

**Query:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| classId |  | YES |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |
| avgScore | number | NO |  |  |
| data | array | NO |  |  |
| &ensp;&ensp;&#124;─ | object |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─score | number | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─endTime | string | NO |  |  |

**Response Demo:**

```json
{
  "message": "",
  "avgScore": 0.0,
  "data": [
    {
      "score": 0.0,
      "endTime": ""
    }
  ]
}
```




---
### getGroupHistoricalScores

> BASIC

**Path:** /api/teacher/group/historical-scores

**Method:** GET

> REQUEST

**Query:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| groupId |  | YES |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |
| avgScore | number | NO |  |  |
| data | array | NO |  |  |
| &ensp;&ensp;&#124;─ | object |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─score | number | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─endTime | string | NO |  |  |

**Response Demo:**

```json
{
  "message": "",
  "avgScore": 0.0,
  "data": [
    {
      "score": 0.0,
      "endTime": ""
    }
  ]
}
```




---
### getClassKnowledgePointStatus

> BASIC

**Path:** /api/teacher/class/knowledge-point-status

**Method:** GET

> REQUEST

**Query:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| classId |  | YES |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |
| data | array | NO |  |  |
| &ensp;&ensp;&#124;─ | object |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─name | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─score | number | NO |  |  |

**Response Demo:**

```json
{
  "message": "",
  "data": [
    {
      "name": "",
      "score": 0.0
    }
  ]
}
```




---
### uploadEssay

> BASIC

**Path:** /api/teacher/upload-essay

**Method:** POST

> REQUEST

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| Content-Type | multipart/form-data | YES |  |

**Query:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| executedDate |  | YES |  |

**Form:**

| name | value | required | type | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| file |  | YES | file |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |
| essayId | integer | NO |  |  |

**Response Demo:**

```json
{
  "message": "",
  "essayId": 0
}
```




---
### getEssays

> BASIC

**Path:** /api/teacher/view-essays

**Method:** GET

> REQUEST



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |
| infoData | array | NO |  |  |
| &ensp;&ensp;&#124;─ | object |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─id | integer | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─title | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─date | string | NO |  |  |

**Response Demo:**

```json
{
  "message": "",
  "infoData": [
    {
      "id": 0,
      "title": "",
      "date": ""
    }
  ]
}
```




---
### getEssayInfo

> BASIC

**Path:** /api/teacher/get-essay

**Method:** GET

> REQUEST

**Query:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| essayId |  | YES |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| inputStreamSource | object | NO |  |  |
| description | string | NO |  |  |
| equality | object | NO |  |  |
| read | boolean | NO |  |  |

**Response Demo:**

```json
{
  "inputStreamSource": {},
  "description": "",
  "equality": {},
  "read": false
}
```





## SystemAdminBusinessController

SystemAdminBusinessController


---
### getSchoolAdminAccounts

> BASIC

**Path:** /api/system-admin/get-school-admin-accounts

**Method:** GET

> REQUEST



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |
| data | array | NO |  |  |
| &ensp;&ensp;&#124;─ | object |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─schoolAdminId | integer | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─userName | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─name | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─email | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─schoolName | string | NO |  |  |

**Response Demo:**

```json
{
  "message": "",
  "data": [
    {
      "schoolAdminId": 0,
      "userName": "",
      "name": "",
      "email": "",
      "schoolName": ""
    }
  ]
}
```




---
### createSchoolAdmin

> BASIC

**Path:** /api/system-admin/create-school-admin

**Method:** POST

> REQUEST

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| Content-Type | application/json | YES |  |

**Request Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| name | string | NO |  |  |
| password | string | NO |  |  |
| schoolName | string | NO |  |  |

**Request Demo:**

```json
{
  "name": "",
  "password": "",
  "schoolName": ""
}
```



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |

**Response Demo:**

```json
{
  "message": ""
}
```




---
### deleteSchoolAdminAccount

> BASIC

**Path:** /api/system-admin/delete-school-admin-account/{id}

**Method:** DELETE

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| id |  |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |

**Response Demo:**

```json
{
  "message": ""
}
```




---
### register

> BASIC

**Path:** /api/system-admin/create

**Method:** POST

> REQUEST

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| Content-Type | application/json | YES |  |

**Request Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| email | string | NO |  |  |
| password | string | NO |  |  |
| username | string | NO |  |  |
| code | string | NO |  |  |

**Request Demo:**

```json
{
  "email": "",
  "password": "",
  "username": "",
  "code": ""
}
```



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |

**Response Demo:**

```json
{
  "message": ""
}
```




---
### updateUsername

> BASIC

**Path:** /api/system-admin/{id}/update-username

**Method:** POST

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| id |  |  |

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| Content-Type | application/json | YES |  |

**Request Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| username | string | NO |  |  |

**Request Demo:**

```json
{
  "username": ""
}
```



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |

**Response Demo:**

```json
{
  "message": ""
}
```





## ImageController

ImageController


---
### uploadImage

> BASIC

**Path:** /api/uploads/image

**Method:** POST

> REQUEST

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| Content-Type | multipart/form-data | YES |  |

**Query:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| type |  | YES |  |

**Form:**

| name | value | required | type | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| image |  | YES | file |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| key | string |  |  |  |

**Response Demo:**

```json
{
  "": ""
}
```




---
### getImage

> BASIC

**Path:** /api/uploads/images/{type}/{imageName}

**Method:** GET

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| type |  |  |
| imageName |  |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| inputStream | object | NO |  |  |
| readable | boolean | NO |  |  |
| open | boolean | NO |  |  |
| file | object | NO |  |  |
| &ensp;&ensp;&#124;─path | string | NO |  | This abstract pathname's normalized pathname string. A normalized<br>pathname string uses the default name-separator character and does not<br>contain any duplicate or redundant separators. |
| &ensp;&ensp;&#124;─name | string | NO |  | Returns the name of the file or directory denoted by this abstract<br>pathname.  This is just the last name in the pathname's name<br>sequence.  If the pathname's name sequence is empty, then the empty<br>string is returned. |
| &ensp;&ensp;&#124;─parent | string | NO |  | Returns the pathname string of this abstract pathname's parent, or<br>{@code null} if this pathname does not name a parent directory.<br><br><p> The <em>parent</em> of an abstract pathname consists of the<br>pathname's prefix, if any, and each name in the pathname's name<br>sequence except for the last.  If the name sequence is empty then<br>the pathname does not name a parent directory. |
| &ensp;&ensp;&#124;─parentFile | object | NO |  | Returns the abstract pathname of this abstract pathname's parent,<br>or{@code null} if this pathname does not name a parent<br>directory.<br><br><p> The <em>parent</em> of an abstract pathname consists of the<br>pathname's prefix, if any, and each name in the pathname's name<br>sequence except for the last.  If the name sequence is empty then<br>the pathname does not name a parent directory. |
| &ensp;&ensp;&ensp;&ensp;&#124;─path | string | NO |  | This abstract pathname's normalized pathname string. A normalized<br>pathname string uses the default name-separator character and does not<br>contain any duplicate or redundant separators. |
| &ensp;&ensp;&ensp;&ensp;&#124;─name | string | NO |  | Returns the name of the file or directory denoted by this abstract<br>pathname.  This is just the last name in the pathname's name<br>sequence.  If the pathname's name sequence is empty, then the empty<br>string is returned. |
| &ensp;&ensp;&ensp;&ensp;&#124;─parent | string | NO |  | Returns the pathname string of this abstract pathname's parent, or<br>{@code null} if this pathname does not name a parent directory.<br><br><p> The <em>parent</em> of an abstract pathname consists of the<br>pathname's prefix, if any, and each name in the pathname's name<br>sequence except for the last.  If the name sequence is empty then<br>the pathname does not name a parent directory. |
| &ensp;&ensp;&ensp;&ensp;&#124;─parentFile | object | NO |  | Returns the abstract pathname of this abstract pathname's parent,<br>or{@code null} if this pathname does not name a parent<br>directory.<br><br><p> The <em>parent</em> of an abstract pathname consists of the<br>pathname's prefix, if any, and each name in the pathname's name<br>sequence except for the last.  If the name sequence is empty then<br>the pathname does not name a parent directory. |
| &ensp;&ensp;&ensp;&ensp;&#124;─absolute | boolean | NO |  | Tests whether this abstract pathname is absolute.  The definition of<br>absolute pathname is system dependent.  On UNIX systems, a pathname is<br>absolute if its prefix is{@code "/"}.  On Microsoft Windows systems, a<br>pathname is absolute if its prefix is a drive specifier followed by<br>{@code "\\"}, or if its prefix is{@code "\\\\"}. |
| &ensp;&ensp;&ensp;&ensp;&#124;─absolutePath | string | NO |  | Returns the absolute pathname string of this abstract pathname.<br><br><p> If this abstract pathname is already absolute, then the pathname<br>string is simply returned as if by the{@link #getPath}<br>method.  If this abstract pathname is the empty abstract pathname then<br>the pathname string of the current user directory, which is named by the<br>system property{@code user.dir}, is returned.  Otherwise this<br>pathname is resolved in a system-dependent way.  On UNIX systems, a<br>relative pathname is made absolute by resolving it against the current<br>user directory.  On Microsoft Windows systems, a relative pathname is made absolute<br>by resolving it against the current directory of the drive named by the<br>pathname, if any; if not, it is resolved against the current user<br>directory. |
| &ensp;&ensp;&ensp;&ensp;&#124;─absoluteFile | object | NO |  | Returns the absolute form of this abstract pathname.  Equivalent to<br><code>new&nbsp;File(this.{@link #getAbsolutePath})</code>. |
| &ensp;&ensp;&ensp;&ensp;&#124;─canonicalPath | string | NO |  | Returns the canonical pathname string of this abstract pathname.<br><br><p> A canonical pathname is both absolute and unique.  The precise<br>definition of canonical form is system-dependent.  This method first<br>converts this pathname to absolute form if necessary, as if by invoking the<br>{@link #getAbsolutePath} method, and then maps it to its unique form in a<br>system-dependent way.  This typically involves removing redundant names<br>such as{@code "."} and{@code ".."} from the pathname, resolving<br>symbolic links (on UNIX platforms), and converting drive letters to a<br>standard case (on Microsoft Windows platforms).<br><br><p> Every pathname that denotes an existing file or directory has a<br>unique canonical form.  Every pathname that denotes a nonexistent file<br>or directory also has a unique canonical form.  The canonical form of<br>the pathname of a nonexistent file or directory may be different from<br>the canonical form of the same pathname after the file or directory is<br>created.  Similarly, the canonical form of the pathname of an existing<br>file or directory may be different from the canonical form of the same<br>pathname after the file or directory is deleted. |
| &ensp;&ensp;&ensp;&ensp;&#124;─canonicalFile | object | NO |  | Returns the canonical form of this abstract pathname.  Equivalent to<br><code>new&nbsp;File(this.{@link #getCanonicalPath})</code>. |
| &ensp;&ensp;&ensp;&ensp;&#124;─directory | boolean | NO |  | Tests whether the file denoted by this abstract pathname is a<br>directory.<br><br><p> Where it is required to distinguish an I/O exception from the case<br>that the file is not a directory, or where several attributes of the<br>same file are required at the same time, then the{@link<br>    * java.nio.file.Files#readAttributes(Path,Class,LinkOption[])<br>    * Files.readAttributes} method may be used. |
| &ensp;&ensp;&ensp;&ensp;&#124;─file | boolean | NO |  | Tests whether the file denoted by this abstract pathname is a normal<br>file.  A file is <em>normal</em> if it is not a directory and, in<br>addition, satisfies other system-dependent criteria.  Any non-directory<br>file created by a Java application is guaranteed to be a normal file.<br><br><p> Where it is required to distinguish an I/O exception from the case<br>that the file is not a normal file, or where several attributes of the<br>same file are required at the same time, then the{@link<br>    * java.nio.file.Files#readAttributes(Path,Class,LinkOption[])<br>    * Files.readAttributes} method may be used. |
| &ensp;&ensp;&ensp;&ensp;&#124;─hidden | boolean | NO |  | Tests whether the file named by this abstract pathname is a hidden<br>file.  The exact definition of <em>hidden</em> is system-dependent.  On<br>UNIX systems, a file is considered to be hidden if its name begins with<br>a period character ({@code '.'}).  On Microsoft Windows systems, a file is<br>considered to be hidden if it has been marked as such in the filesystem. |
| &ensp;&ensp;&ensp;&ensp;&#124;─lastModified | integer | NO |  | Sets the last-modified time of the file or directory named by this<br>abstract pathname.<br><br><p> All platforms support file-modification times to the nearest second,<br>but some provide more precision.  The argument will be truncated to fit<br>the supported precision.  If the operation succeeds and no intervening<br>operations on the file take place, then the next invocation of the<br>{@link #lastModified} method will return the (possibly<br>truncated){@code time} argument that was passed to this method. |
| &ensp;&ensp;&ensp;&ensp;&#124;─writable | boolean | NO |  | A convenience method to set the owner's write permission for this abstract<br>pathname. On some platforms it may be possible to start the Java virtual<br>machine with special privileges that allow it to modify files that<br>disallow write operations.<br><br><p> An invocation of this method of the form{@code file.setWritable(arg)}<br>behaves in exactly the same way as the invocation<br><br>{@snippet lang=java :<br>    *     file.setWritable(arg, true)<br>    * } |
| &ensp;&ensp;&ensp;&ensp;&#124;─readable | boolean | NO |  | A convenience method to set the owner's read permission for this abstract<br>pathname. On some platforms it may be possible to start the Java virtual<br>machine with special privileges that allow it to read files that are<br>marked as unreadable.<br><br><p>An invocation of this method of the form{@code file.setReadable(arg)}<br>behaves in exactly the same way as the invocation<br><br>{@snippet lang=java :<br>    *     file.setReadable(arg, true)<br>    * }<br><br><p> If the platform supports setting a file's read permission, but<br>the user does not have permission to change the access permissions of<br>this abstract pathname, then the operation will fail. If the platform<br>does not support setting a file's read permission, this method does<br>nothing and returns the value of the{@code readable} parameter. |
| &ensp;&ensp;&ensp;&ensp;&#124;─executable | boolean | NO |  | A convenience method to set the owner's execute permission for this<br>abstract pathname. On some platforms it may be possible to start the Java<br>virtual machine with special privileges that allow it to execute files<br>that are not marked executable.<br><br><p>An invocation of this method of the form{@code file.setExcutable(arg)}<br>behaves in exactly the same way as the invocation<br><br>{@snippet lang=java :<br>    *     file.setExecutable(arg, true)<br>    * }<br><br><p> If the platform supports setting a file's execute permission, but<br>the user does not have permission to change the access permissions of<br>this abstract pathname, then the operation will fail. If the platform<br>does not support setting a file's execute permission, this method does<br>nothing and returns the value of the{@code executable} parameter. |
| &ensp;&ensp;&ensp;&ensp;&#124;─totalSpace | integer | NO |  | Returns the size of the partition <a href="#partName">named</a> by this<br>abstract pathname. If the total number of bytes in the partition is<br>greater than{@link Long#MAX_VALUE}, then{@code Long.MAX_VALUE} will be<br>returned. |
| &ensp;&ensp;&ensp;&ensp;&#124;─freeSpace | integer | NO |  | Returns the number of unallocated bytes in the partition <a<br>href="#partName">named</a> by this abstract path name.  If the<br>number of unallocated bytes in the partition is greater than<br>{@link Long#MAX_VALUE}, then{@code Long.MAX_VALUE} will be returned.<br><br><p> The returned number of unallocated bytes is a hint, but not<br>a guarantee, that it is possible to use most or any of these<br>bytes.  The number of unallocated bytes is most likely to be<br>accurate immediately after this call.  It is likely to be made<br>inaccurate by any external I/O operations including those made<br>on the system outside of this virtual machine.  This method<br>makes no guarantee that write operations to this file system<br>will succeed. |
| &ensp;&ensp;&ensp;&ensp;&#124;─usableSpace | integer | NO |  | Returns the number of bytes available to this virtual machine on the<br>partition <a href="#partName">named</a> by this abstract pathname.  If<br>the number of available bytes in the partition is greater than<br>{@link Long#MAX_VALUE}, then{@code Long.MAX_VALUE} will be returned.<br>When possible, this method checks for write permissions and other<br>operating system restrictions and will therefore usually provide a more<br>accurate estimate of how much new data can actually be written than<br>{@link #getFreeSpace}.<br><br><p> The returned number of available bytes is a hint, but not a<br>guarantee, that it is possible to use most or any of these bytes.  The<br>number of available bytes is most likely to be accurate immediately<br>after this call.  It is likely to be made inaccurate by any external<br>I/O operations including those made on the system outside of this<br>virtual machine.  This method makes no guarantee that write operations<br>to this file system will succeed. |
| &ensp;&ensp;&#124;─absolute | boolean | NO |  | Tests whether this abstract pathname is absolute.  The definition of<br>absolute pathname is system dependent.  On UNIX systems, a pathname is<br>absolute if its prefix is{@code "/"}.  On Microsoft Windows systems, a<br>pathname is absolute if its prefix is a drive specifier followed by<br>{@code "\\"}, or if its prefix is{@code "\\\\"}. |
| &ensp;&ensp;&#124;─absolutePath | string | NO |  | Returns the absolute pathname string of this abstract pathname.<br><br><p> If this abstract pathname is already absolute, then the pathname<br>string is simply returned as if by the{@link #getPath}<br>method.  If this abstract pathname is the empty abstract pathname then<br>the pathname string of the current user directory, which is named by the<br>system property{@code user.dir}, is returned.  Otherwise this<br>pathname is resolved in a system-dependent way.  On UNIX systems, a<br>relative pathname is made absolute by resolving it against the current<br>user directory.  On Microsoft Windows systems, a relative pathname is made absolute<br>by resolving it against the current directory of the drive named by the<br>pathname, if any; if not, it is resolved against the current user<br>directory. |
| &ensp;&ensp;&#124;─absoluteFile | object | NO |  | Returns the absolute form of this abstract pathname.  Equivalent to<br><code>new&nbsp;File(this.{@link #getAbsolutePath})</code>. |
| &ensp;&ensp;&ensp;&ensp;&#124;─path | string | NO |  | This abstract pathname's normalized pathname string. A normalized<br>pathname string uses the default name-separator character and does not<br>contain any duplicate or redundant separators. |
| &ensp;&ensp;&ensp;&ensp;&#124;─name | string | NO |  | Returns the name of the file or directory denoted by this abstract<br>pathname.  This is just the last name in the pathname's name<br>sequence.  If the pathname's name sequence is empty, then the empty<br>string is returned. |
| &ensp;&ensp;&ensp;&ensp;&#124;─parent | string | NO |  | Returns the pathname string of this abstract pathname's parent, or<br>{@code null} if this pathname does not name a parent directory.<br><br><p> The <em>parent</em> of an abstract pathname consists of the<br>pathname's prefix, if any, and each name in the pathname's name<br>sequence except for the last.  If the name sequence is empty then<br>the pathname does not name a parent directory. |
| &ensp;&ensp;&ensp;&ensp;&#124;─parentFile | object | NO |  | Returns the abstract pathname of this abstract pathname's parent,<br>or{@code null} if this pathname does not name a parent<br>directory.<br><br><p> The <em>parent</em> of an abstract pathname consists of the<br>pathname's prefix, if any, and each name in the pathname's name<br>sequence except for the last.  If the name sequence is empty then<br>the pathname does not name a parent directory. |
| &ensp;&ensp;&ensp;&ensp;&#124;─absolute | boolean | NO |  | Tests whether this abstract pathname is absolute.  The definition of<br>absolute pathname is system dependent.  On UNIX systems, a pathname is<br>absolute if its prefix is{@code "/"}.  On Microsoft Windows systems, a<br>pathname is absolute if its prefix is a drive specifier followed by<br>{@code "\\"}, or if its prefix is{@code "\\\\"}. |
| &ensp;&ensp;&ensp;&ensp;&#124;─absolutePath | string | NO |  | Returns the absolute pathname string of this abstract pathname.<br><br><p> If this abstract pathname is already absolute, then the pathname<br>string is simply returned as if by the{@link #getPath}<br>method.  If this abstract pathname is the empty abstract pathname then<br>the pathname string of the current user directory, which is named by the<br>system property{@code user.dir}, is returned.  Otherwise this<br>pathname is resolved in a system-dependent way.  On UNIX systems, a<br>relative pathname is made absolute by resolving it against the current<br>user directory.  On Microsoft Windows systems, a relative pathname is made absolute<br>by resolving it against the current directory of the drive named by the<br>pathname, if any; if not, it is resolved against the current user<br>directory. |
| &ensp;&ensp;&ensp;&ensp;&#124;─absoluteFile | object | NO |  | Returns the absolute form of this abstract pathname.  Equivalent to<br><code>new&nbsp;File(this.{@link #getAbsolutePath})</code>. |
| &ensp;&ensp;&ensp;&ensp;&#124;─canonicalPath | string | NO |  | Returns the canonical pathname string of this abstract pathname.<br><br><p> A canonical pathname is both absolute and unique.  The precise<br>definition of canonical form is system-dependent.  This method first<br>converts this pathname to absolute form if necessary, as if by invoking the<br>{@link #getAbsolutePath} method, and then maps it to its unique form in a<br>system-dependent way.  This typically involves removing redundant names<br>such as{@code "."} and{@code ".."} from the pathname, resolving<br>symbolic links (on UNIX platforms), and converting drive letters to a<br>standard case (on Microsoft Windows platforms).<br><br><p> Every pathname that denotes an existing file or directory has a<br>unique canonical form.  Every pathname that denotes a nonexistent file<br>or directory also has a unique canonical form.  The canonical form of<br>the pathname of a nonexistent file or directory may be different from<br>the canonical form of the same pathname after the file or directory is<br>created.  Similarly, the canonical form of the pathname of an existing<br>file or directory may be different from the canonical form of the same<br>pathname after the file or directory is deleted. |
| &ensp;&ensp;&ensp;&ensp;&#124;─canonicalFile | object | NO |  | Returns the canonical form of this abstract pathname.  Equivalent to<br><code>new&nbsp;File(this.{@link #getCanonicalPath})</code>. |
| &ensp;&ensp;&ensp;&ensp;&#124;─directory | boolean | NO |  | Tests whether the file denoted by this abstract pathname is a<br>directory.<br><br><p> Where it is required to distinguish an I/O exception from the case<br>that the file is not a directory, or where several attributes of the<br>same file are required at the same time, then the{@link<br>    * java.nio.file.Files#readAttributes(Path,Class,LinkOption[])<br>    * Files.readAttributes} method may be used. |
| &ensp;&ensp;&ensp;&ensp;&#124;─file | boolean | NO |  | Tests whether the file denoted by this abstract pathname is a normal<br>file.  A file is <em>normal</em> if it is not a directory and, in<br>addition, satisfies other system-dependent criteria.  Any non-directory<br>file created by a Java application is guaranteed to be a normal file.<br><br><p> Where it is required to distinguish an I/O exception from the case<br>that the file is not a normal file, or where several attributes of the<br>same file are required at the same time, then the{@link<br>    * java.nio.file.Files#readAttributes(Path,Class,LinkOption[])<br>    * Files.readAttributes} method may be used. |
| &ensp;&ensp;&ensp;&ensp;&#124;─hidden | boolean | NO |  | Tests whether the file named by this abstract pathname is a hidden<br>file.  The exact definition of <em>hidden</em> is system-dependent.  On<br>UNIX systems, a file is considered to be hidden if its name begins with<br>a period character ({@code '.'}).  On Microsoft Windows systems, a file is<br>considered to be hidden if it has been marked as such in the filesystem. |
| &ensp;&ensp;&ensp;&ensp;&#124;─lastModified | integer | NO |  | Sets the last-modified time of the file or directory named by this<br>abstract pathname.<br><br><p> All platforms support file-modification times to the nearest second,<br>but some provide more precision.  The argument will be truncated to fit<br>the supported precision.  If the operation succeeds and no intervening<br>operations on the file take place, then the next invocation of the<br>{@link #lastModified} method will return the (possibly<br>truncated){@code time} argument that was passed to this method. |
| &ensp;&ensp;&ensp;&ensp;&#124;─writable | boolean | NO |  | A convenience method to set the owner's write permission for this abstract<br>pathname. On some platforms it may be possible to start the Java virtual<br>machine with special privileges that allow it to modify files that<br>disallow write operations.<br><br><p> An invocation of this method of the form{@code file.setWritable(arg)}<br>behaves in exactly the same way as the invocation<br><br>{@snippet lang=java :<br>    *     file.setWritable(arg, true)<br>    * } |
| &ensp;&ensp;&ensp;&ensp;&#124;─readable | boolean | NO |  | A convenience method to set the owner's read permission for this abstract<br>pathname. On some platforms it may be possible to start the Java virtual<br>machine with special privileges that allow it to read files that are<br>marked as unreadable.<br><br><p>An invocation of this method of the form{@code file.setReadable(arg)}<br>behaves in exactly the same way as the invocation<br><br>{@snippet lang=java :<br>    *     file.setReadable(arg, true)<br>    * }<br><br><p> If the platform supports setting a file's read permission, but<br>the user does not have permission to change the access permissions of<br>this abstract pathname, then the operation will fail. If the platform<br>does not support setting a file's read permission, this method does<br>nothing and returns the value of the{@code readable} parameter. |
| &ensp;&ensp;&ensp;&ensp;&#124;─executable | boolean | NO |  | A convenience method to set the owner's execute permission for this<br>abstract pathname. On some platforms it may be possible to start the Java<br>virtual machine with special privileges that allow it to execute files<br>that are not marked executable.<br><br><p>An invocation of this method of the form{@code file.setExcutable(arg)}<br>behaves in exactly the same way as the invocation<br><br>{@snippet lang=java :<br>    *     file.setExecutable(arg, true)<br>    * }<br><br><p> If the platform supports setting a file's execute permission, but<br>the user does not have permission to change the access permissions of<br>this abstract pathname, then the operation will fail. If the platform<br>does not support setting a file's execute permission, this method does<br>nothing and returns the value of the{@code executable} parameter. |
| &ensp;&ensp;&ensp;&ensp;&#124;─totalSpace | integer | NO |  | Returns the size of the partition <a href="#partName">named</a> by this<br>abstract pathname. If the total number of bytes in the partition is<br>greater than{@link Long#MAX_VALUE}, then{@code Long.MAX_VALUE} will be<br>returned. |
| &ensp;&ensp;&ensp;&ensp;&#124;─freeSpace | integer | NO |  | Returns the number of unallocated bytes in the partition <a<br>href="#partName">named</a> by this abstract path name.  If the<br>number of unallocated bytes in the partition is greater than<br>{@link Long#MAX_VALUE}, then{@code Long.MAX_VALUE} will be returned.<br><br><p> The returned number of unallocated bytes is a hint, but not<br>a guarantee, that it is possible to use most or any of these<br>bytes.  The number of unallocated bytes is most likely to be<br>accurate immediately after this call.  It is likely to be made<br>inaccurate by any external I/O operations including those made<br>on the system outside of this virtual machine.  This method<br>makes no guarantee that write operations to this file system<br>will succeed. |
| &ensp;&ensp;&ensp;&ensp;&#124;─usableSpace | integer | NO |  | Returns the number of bytes available to this virtual machine on the<br>partition <a href="#partName">named</a> by this abstract pathname.  If<br>the number of available bytes in the partition is greater than<br>{@link Long#MAX_VALUE}, then{@code Long.MAX_VALUE} will be returned.<br>When possible, this method checks for write permissions and other<br>operating system restrictions and will therefore usually provide a more<br>accurate estimate of how much new data can actually be written than<br>{@link #getFreeSpace}.<br><br><p> The returned number of available bytes is a hint, but not a<br>guarantee, that it is possible to use most or any of these bytes.  The<br>number of available bytes is most likely to be accurate immediately<br>after this call.  It is likely to be made inaccurate by any external<br>I/O operations including those made on the system outside of this<br>virtual machine.  This method makes no guarantee that write operations<br>to this file system will succeed. |
| &ensp;&ensp;&#124;─canonicalPath | string | NO |  | Returns the canonical pathname string of this abstract pathname.<br><br><p> A canonical pathname is both absolute and unique.  The precise<br>definition of canonical form is system-dependent.  This method first<br>converts this pathname to absolute form if necessary, as if by invoking the<br>{@link #getAbsolutePath} method, and then maps it to its unique form in a<br>system-dependent way.  This typically involves removing redundant names<br>such as{@code "."} and{@code ".."} from the pathname, resolving<br>symbolic links (on UNIX platforms), and converting drive letters to a<br>standard case (on Microsoft Windows platforms).<br><br><p> Every pathname that denotes an existing file or directory has a<br>unique canonical form.  Every pathname that denotes a nonexistent file<br>or directory also has a unique canonical form.  The canonical form of<br>the pathname of a nonexistent file or directory may be different from<br>the canonical form of the same pathname after the file or directory is<br>created.  Similarly, the canonical form of the pathname of an existing<br>file or directory may be different from the canonical form of the same<br>pathname after the file or directory is deleted. |
| &ensp;&ensp;&#124;─canonicalFile | object | NO |  | Returns the canonical form of this abstract pathname.  Equivalent to<br><code>new&nbsp;File(this.{@link #getCanonicalPath})</code>. |
| &ensp;&ensp;&ensp;&ensp;&#124;─path | string | NO |  | This abstract pathname's normalized pathname string. A normalized<br>pathname string uses the default name-separator character and does not<br>contain any duplicate or redundant separators. |
| &ensp;&ensp;&ensp;&ensp;&#124;─name | string | NO |  | Returns the name of the file or directory denoted by this abstract<br>pathname.  This is just the last name in the pathname's name<br>sequence.  If the pathname's name sequence is empty, then the empty<br>string is returned. |
| &ensp;&ensp;&ensp;&ensp;&#124;─parent | string | NO |  | Returns the pathname string of this abstract pathname's parent, or<br>{@code null} if this pathname does not name a parent directory.<br><br><p> The <em>parent</em> of an abstract pathname consists of the<br>pathname's prefix, if any, and each name in the pathname's name<br>sequence except for the last.  If the name sequence is empty then<br>the pathname does not name a parent directory. |
| &ensp;&ensp;&ensp;&ensp;&#124;─parentFile | object | NO |  | Returns the abstract pathname of this abstract pathname's parent,<br>or{@code null} if this pathname does not name a parent<br>directory.<br><br><p> The <em>parent</em> of an abstract pathname consists of the<br>pathname's prefix, if any, and each name in the pathname's name<br>sequence except for the last.  If the name sequence is empty then<br>the pathname does not name a parent directory. |
| &ensp;&ensp;&ensp;&ensp;&#124;─absolute | boolean | NO |  | Tests whether this abstract pathname is absolute.  The definition of<br>absolute pathname is system dependent.  On UNIX systems, a pathname is<br>absolute if its prefix is{@code "/"}.  On Microsoft Windows systems, a<br>pathname is absolute if its prefix is a drive specifier followed by<br>{@code "\\"}, or if its prefix is{@code "\\\\"}. |
| &ensp;&ensp;&ensp;&ensp;&#124;─absolutePath | string | NO |  | Returns the absolute pathname string of this abstract pathname.<br><br><p> If this abstract pathname is already absolute, then the pathname<br>string is simply returned as if by the{@link #getPath}<br>method.  If this abstract pathname is the empty abstract pathname then<br>the pathname string of the current user directory, which is named by the<br>system property{@code user.dir}, is returned.  Otherwise this<br>pathname is resolved in a system-dependent way.  On UNIX systems, a<br>relative pathname is made absolute by resolving it against the current<br>user directory.  On Microsoft Windows systems, a relative pathname is made absolute<br>by resolving it against the current directory of the drive named by the<br>pathname, if any; if not, it is resolved against the current user<br>directory. |
| &ensp;&ensp;&ensp;&ensp;&#124;─absoluteFile | object | NO |  | Returns the absolute form of this abstract pathname.  Equivalent to<br><code>new&nbsp;File(this.{@link #getAbsolutePath})</code>. |
| &ensp;&ensp;&ensp;&ensp;&#124;─canonicalPath | string | NO |  | Returns the canonical pathname string of this abstract pathname.<br><br><p> A canonical pathname is both absolute and unique.  The precise<br>definition of canonical form is system-dependent.  This method first<br>converts this pathname to absolute form if necessary, as if by invoking the<br>{@link #getAbsolutePath} method, and then maps it to its unique form in a<br>system-dependent way.  This typically involves removing redundant names<br>such as{@code "."} and{@code ".."} from the pathname, resolving<br>symbolic links (on UNIX platforms), and converting drive letters to a<br>standard case (on Microsoft Windows platforms).<br><br><p> Every pathname that denotes an existing file or directory has a<br>unique canonical form.  Every pathname that denotes a nonexistent file<br>or directory also has a unique canonical form.  The canonical form of<br>the pathname of a nonexistent file or directory may be different from<br>the canonical form of the same pathname after the file or directory is<br>created.  Similarly, the canonical form of the pathname of an existing<br>file or directory may be different from the canonical form of the same<br>pathname after the file or directory is deleted. |
| &ensp;&ensp;&ensp;&ensp;&#124;─canonicalFile | object | NO |  | Returns the canonical form of this abstract pathname.  Equivalent to<br><code>new&nbsp;File(this.{@link #getCanonicalPath})</code>. |
| &ensp;&ensp;&ensp;&ensp;&#124;─directory | boolean | NO |  | Tests whether the file denoted by this abstract pathname is a<br>directory.<br><br><p> Where it is required to distinguish an I/O exception from the case<br>that the file is not a directory, or where several attributes of the<br>same file are required at the same time, then the{@link<br>    * java.nio.file.Files#readAttributes(Path,Class,LinkOption[])<br>    * Files.readAttributes} method may be used. |
| &ensp;&ensp;&ensp;&ensp;&#124;─file | boolean | NO |  | Tests whether the file denoted by this abstract pathname is a normal<br>file.  A file is <em>normal</em> if it is not a directory and, in<br>addition, satisfies other system-dependent criteria.  Any non-directory<br>file created by a Java application is guaranteed to be a normal file.<br><br><p> Where it is required to distinguish an I/O exception from the case<br>that the file is not a normal file, or where several attributes of the<br>same file are required at the same time, then the{@link<br>    * java.nio.file.Files#readAttributes(Path,Class,LinkOption[])<br>    * Files.readAttributes} method may be used. |
| &ensp;&ensp;&ensp;&ensp;&#124;─hidden | boolean | NO |  | Tests whether the file named by this abstract pathname is a hidden<br>file.  The exact definition of <em>hidden</em> is system-dependent.  On<br>UNIX systems, a file is considered to be hidden if its name begins with<br>a period character ({@code '.'}).  On Microsoft Windows systems, a file is<br>considered to be hidden if it has been marked as such in the filesystem. |
| &ensp;&ensp;&ensp;&ensp;&#124;─lastModified | integer | NO |  | Sets the last-modified time of the file or directory named by this<br>abstract pathname.<br><br><p> All platforms support file-modification times to the nearest second,<br>but some provide more precision.  The argument will be truncated to fit<br>the supported precision.  If the operation succeeds and no intervening<br>operations on the file take place, then the next invocation of the<br>{@link #lastModified} method will return the (possibly<br>truncated){@code time} argument that was passed to this method. |
| &ensp;&ensp;&ensp;&ensp;&#124;─writable | boolean | NO |  | A convenience method to set the owner's write permission for this abstract<br>pathname. On some platforms it may be possible to start the Java virtual<br>machine with special privileges that allow it to modify files that<br>disallow write operations.<br><br><p> An invocation of this method of the form{@code file.setWritable(arg)}<br>behaves in exactly the same way as the invocation<br><br>{@snippet lang=java :<br>    *     file.setWritable(arg, true)<br>    * } |
| &ensp;&ensp;&ensp;&ensp;&#124;─readable | boolean | NO |  | A convenience method to set the owner's read permission for this abstract<br>pathname. On some platforms it may be possible to start the Java virtual<br>machine with special privileges that allow it to read files that are<br>marked as unreadable.<br><br><p>An invocation of this method of the form{@code file.setReadable(arg)}<br>behaves in exactly the same way as the invocation<br><br>{@snippet lang=java :<br>    *     file.setReadable(arg, true)<br>    * }<br><br><p> If the platform supports setting a file's read permission, but<br>the user does not have permission to change the access permissions of<br>this abstract pathname, then the operation will fail. If the platform<br>does not support setting a file's read permission, this method does<br>nothing and returns the value of the{@code readable} parameter. |
| &ensp;&ensp;&ensp;&ensp;&#124;─executable | boolean | NO |  | A convenience method to set the owner's execute permission for this<br>abstract pathname. On some platforms it may be possible to start the Java<br>virtual machine with special privileges that allow it to execute files<br>that are not marked executable.<br><br><p>An invocation of this method of the form{@code file.setExcutable(arg)}<br>behaves in exactly the same way as the invocation<br><br>{@snippet lang=java :<br>    *     file.setExecutable(arg, true)<br>    * }<br><br><p> If the platform supports setting a file's execute permission, but<br>the user does not have permission to change the access permissions of<br>this abstract pathname, then the operation will fail. If the platform<br>does not support setting a file's execute permission, this method does<br>nothing and returns the value of the{@code executable} parameter. |
| &ensp;&ensp;&ensp;&ensp;&#124;─totalSpace | integer | NO |  | Returns the size of the partition <a href="#partName">named</a> by this<br>abstract pathname. If the total number of bytes in the partition is<br>greater than{@link Long#MAX_VALUE}, then{@code Long.MAX_VALUE} will be<br>returned. |
| &ensp;&ensp;&ensp;&ensp;&#124;─freeSpace | integer | NO |  | Returns the number of unallocated bytes in the partition <a<br>href="#partName">named</a> by this abstract path name.  If the<br>number of unallocated bytes in the partition is greater than<br>{@link Long#MAX_VALUE}, then{@code Long.MAX_VALUE} will be returned.<br><br><p> The returned number of unallocated bytes is a hint, but not<br>a guarantee, that it is possible to use most or any of these<br>bytes.  The number of unallocated bytes is most likely to be<br>accurate immediately after this call.  It is likely to be made<br>inaccurate by any external I/O operations including those made<br>on the system outside of this virtual machine.  This method<br>makes no guarantee that write operations to this file system<br>will succeed. |
| &ensp;&ensp;&ensp;&ensp;&#124;─usableSpace | integer | NO |  | Returns the number of bytes available to this virtual machine on the<br>partition <a href="#partName">named</a> by this abstract pathname.  If<br>the number of available bytes in the partition is greater than<br>{@link Long#MAX_VALUE}, then{@code Long.MAX_VALUE} will be returned.<br>When possible, this method checks for write permissions and other<br>operating system restrictions and will therefore usually provide a more<br>accurate estimate of how much new data can actually be written than<br>{@link #getFreeSpace}.<br><br><p> The returned number of available bytes is a hint, but not a<br>guarantee, that it is possible to use most or any of these bytes.  The<br>number of available bytes is most likely to be accurate immediately<br>after this call.  It is likely to be made inaccurate by any external<br>I/O operations including those made on the system outside of this<br>virtual machine.  This method makes no guarantee that write operations<br>to this file system will succeed. |
| &ensp;&ensp;&#124;─directory | boolean | NO |  | Tests whether the file denoted by this abstract pathname is a<br>directory.<br><br><p> Where it is required to distinguish an I/O exception from the case<br>that the file is not a directory, or where several attributes of the<br>same file are required at the same time, then the{@link<br>    * java.nio.file.Files#readAttributes(Path,Class,LinkOption[])<br>    * Files.readAttributes} method may be used. |
| &ensp;&ensp;&#124;─file | boolean | NO |  | Tests whether the file denoted by this abstract pathname is a normal<br>file.  A file is <em>normal</em> if it is not a directory and, in<br>addition, satisfies other system-dependent criteria.  Any non-directory<br>file created by a Java application is guaranteed to be a normal file.<br><br><p> Where it is required to distinguish an I/O exception from the case<br>that the file is not a normal file, or where several attributes of the<br>same file are required at the same time, then the{@link<br>    * java.nio.file.Files#readAttributes(Path,Class,LinkOption[])<br>    * Files.readAttributes} method may be used. |
| &ensp;&ensp;&#124;─hidden | boolean | NO |  | Tests whether the file named by this abstract pathname is a hidden<br>file.  The exact definition of <em>hidden</em> is system-dependent.  On<br>UNIX systems, a file is considered to be hidden if its name begins with<br>a period character ({@code '.'}).  On Microsoft Windows systems, a file is<br>considered to be hidden if it has been marked as such in the filesystem. |
| &ensp;&ensp;&#124;─lastModified | integer | NO |  | Sets the last-modified time of the file or directory named by this<br>abstract pathname.<br><br><p> All platforms support file-modification times to the nearest second,<br>but some provide more precision.  The argument will be truncated to fit<br>the supported precision.  If the operation succeeds and no intervening<br>operations on the file take place, then the next invocation of the<br>{@link #lastModified} method will return the (possibly<br>truncated){@code time} argument that was passed to this method. |
| &ensp;&ensp;&#124;─writable | boolean | NO |  | A convenience method to set the owner's write permission for this abstract<br>pathname. On some platforms it may be possible to start the Java virtual<br>machine with special privileges that allow it to modify files that<br>disallow write operations.<br><br><p> An invocation of this method of the form{@code file.setWritable(arg)}<br>behaves in exactly the same way as the invocation<br><br>{@snippet lang=java :<br>    *     file.setWritable(arg, true)<br>    * } |
| &ensp;&ensp;&#124;─readable | boolean | NO |  | A convenience method to set the owner's read permission for this abstract<br>pathname. On some platforms it may be possible to start the Java virtual<br>machine with special privileges that allow it to read files that are<br>marked as unreadable.<br><br><p>An invocation of this method of the form{@code file.setReadable(arg)}<br>behaves in exactly the same way as the invocation<br><br>{@snippet lang=java :<br>    *     file.setReadable(arg, true)<br>    * }<br><br><p> If the platform supports setting a file's read permission, but<br>the user does not have permission to change the access permissions of<br>this abstract pathname, then the operation will fail. If the platform<br>does not support setting a file's read permission, this method does<br>nothing and returns the value of the{@code readable} parameter. |
| &ensp;&ensp;&#124;─executable | boolean | NO |  | A convenience method to set the owner's execute permission for this<br>abstract pathname. On some platforms it may be possible to start the Java<br>virtual machine with special privileges that allow it to execute files<br>that are not marked executable.<br><br><p>An invocation of this method of the form{@code file.setExcutable(arg)}<br>behaves in exactly the same way as the invocation<br><br>{@snippet lang=java :<br>    *     file.setExecutable(arg, true)<br>    * }<br><br><p> If the platform supports setting a file's execute permission, but<br>the user does not have permission to change the access permissions of<br>this abstract pathname, then the operation will fail. If the platform<br>does not support setting a file's execute permission, this method does<br>nothing and returns the value of the{@code executable} parameter. |
| &ensp;&ensp;&#124;─totalSpace | integer | NO |  | Returns the size of the partition <a href="#partName">named</a> by this<br>abstract pathname. If the total number of bytes in the partition is<br>greater than{@link Long#MAX_VALUE}, then{@code Long.MAX_VALUE} will be<br>returned. |
| &ensp;&ensp;&#124;─freeSpace | integer | NO |  | Returns the number of unallocated bytes in the partition <a<br>href="#partName">named</a> by this abstract path name.  If the<br>number of unallocated bytes in the partition is greater than<br>{@link Long#MAX_VALUE}, then{@code Long.MAX_VALUE} will be returned.<br><br><p> The returned number of unallocated bytes is a hint, but not<br>a guarantee, that it is possible to use most or any of these<br>bytes.  The number of unallocated bytes is most likely to be<br>accurate immediately after this call.  It is likely to be made<br>inaccurate by any external I/O operations including those made<br>on the system outside of this virtual machine.  This method<br>makes no guarantee that write operations to this file system<br>will succeed. |
| &ensp;&ensp;&#124;─usableSpace | integer | NO |  | Returns the number of bytes available to this virtual machine on the<br>partition <a href="#partName">named</a> by this abstract pathname.  If<br>the number of available bytes in the partition is greater than<br>{@link Long#MAX_VALUE}, then{@code Long.MAX_VALUE} will be returned.<br>When possible, this method checks for write permissions and other<br>operating system restrictions and will therefore usually provide a more<br>accurate estimate of how much new data can actually be written than<br>{@link #getFreeSpace}.<br><br><p> The returned number of available bytes is a hint, but not a<br>guarantee, that it is possible to use most or any of these bytes.  The<br>number of available bytes is most likely to be accurate immediately<br>after this call.  It is likely to be made inaccurate by any external<br>I/O operations including those made on the system outside of this<br>virtual machine.  This method makes no guarantee that write operations<br>to this file system will succeed. |
| uRL | string | NO |  |  |
| uRI | object | NO |  |  |
| &ensp;&ensp;&#124;─string | string | NO |  | The string form of this URI. |
| &ensp;&ensp;&#124;─absolute | boolean | NO |  | Tells whether or not this URI is absolute.<br><br><p> A URI is absolute if, and only if, it has a scheme component. </p> |
| &ensp;&ensp;&#124;─opaque | boolean | NO |  | Tells whether or not this URI is opaque.<br><br><p> A URI is opaque if, and only if, it is absolute and its<br>scheme-specific part does not begin with a slash character ('/').<br>An opaque URI has a scheme, a scheme-specific part, and possibly<br>a fragment; all other components are undefined. </p> |
| &ensp;&ensp;&#124;─rawSchemeSpecificPart | string | NO |  | Returns the raw scheme-specific part of this URI.  The scheme-specific<br>part is never undefined, though it may be empty.<br><br><p> The scheme-specific part of a URI only contains legal URI<br>characters. </p> |
| &ensp;&ensp;&#124;─rawAuthority | string | NO |  | Returns the raw authority component of this URI.<br><br><p> The authority component of a URI, if defined, only contains the<br>commercial-at character ({@code '@'}) and characters in the<br><i>unreserved</i>, <i>punct</i>, <i>escaped</i>, and <i>other</i><br>categories.  If the authority is server-based then it is further<br>constrained to have valid user-information, host, and port<br>components. </p> |
| &ensp;&ensp;&#124;─rawUserInfo | string | NO |  | Returns the raw user-information component of this URI.<br><br><p> The user-information component of a URI, if defined, only contains<br>characters in the <i>unreserved</i>, <i>punct</i>, <i>escaped</i>, and<br><i>other</i> categories. </p> |
| &ensp;&ensp;&#124;─rawPath | string | NO |  | Returns the raw path component of this URI.<br><br><p> The path component of a URI, if defined, only contains the slash<br>character ({@code '/'}), the commercial-at character ({@code '@'}),<br>and characters in the <i>unreserved</i>, <i>punct</i>, <i>escaped</i>,<br>and <i>other</i> categories. </p> |
| &ensp;&ensp;&#124;─rawQuery | string | NO |  | Returns the raw query component of this URI.<br><br><p> The query component of a URI, if defined, only contains legal URI<br>characters. </p> |
| &ensp;&ensp;&#124;─rawFragment | string | NO |  | Returns the raw fragment component of this URI.<br><br><p> The fragment component of a URI, if defined, only contains legal URI<br>characters. </p> |
| contentAsByteArray | array | NO |  |  |
| &ensp;&ensp;&#124;─ | integer |  |  |  |
| filename | string | NO |  |  |
| description | string | NO |  |  |

**Response Demo:**

```json
{
  "inputStream": {},
  "readable": false,
  "open": false,
  "file": {
    "path": "",
    "name": "",
    "parent": "",
    "parentFile": {
      "path": "",
      "name": "",
      "parent": "",
      "parentFile": {},
      "absolute": false,
      "absolutePath": "",
      "absoluteFile": {},
      "canonicalPath": "",
      "canonicalFile": {},
      "directory": false,
      "file": false,
      "hidden": false,
      "lastModified": 0,
      "writable": false,
      "readable": false,
      "executable": false,
      "totalSpace": 0,
      "freeSpace": 0,
      "usableSpace": 0
    },
    "absolute": false,
    "absolutePath": "",
    "absoluteFile": {
      "path": "",
      "name": "",
      "parent": "",
      "parentFile": {},
      "absolute": false,
      "absolutePath": "",
      "absoluteFile": {},
      "canonicalPath": "",
      "canonicalFile": {},
      "directory": false,
      "file": false,
      "hidden": false,
      "lastModified": 0,
      "writable": false,
      "readable": false,
      "executable": false,
      "totalSpace": 0,
      "freeSpace": 0,
      "usableSpace": 0
    },
    "canonicalPath": "",
    "canonicalFile": {
      "path": "",
      "name": "",
      "parent": "",
      "parentFile": {},
      "absolute": false,
      "absolutePath": "",
      "absoluteFile": {},
      "canonicalPath": "",
      "canonicalFile": {},
      "directory": false,
      "file": false,
      "hidden": false,
      "lastModified": 0,
      "writable": false,
      "readable": false,
      "executable": false,
      "totalSpace": 0,
      "freeSpace": 0,
      "usableSpace": 0
    },
    "directory": false,
    "file": false,
    "hidden": false,
    "lastModified": 0,
    "writable": false,
    "readable": false,
    "executable": false,
    "totalSpace": 0,
    "freeSpace": 0,
    "usableSpace": 0
  },
  "uRL": "",
  "uRI": {
    "string": "",
    "absolute": false,
    "opaque": false,
    "rawSchemeSpecificPart": "",
    "rawAuthority": "",
    "rawUserInfo": "",
    "rawPath": "",
    "rawQuery": "",
    "rawFragment": ""
  },
  "contentAsByteArray": [
    0
  ],
  "filename": "",
  "description": ""
}
```




---
### deleteImage

> BASIC

**Path:** /api/uploads/image/{type}/{imageName}

**Method:** DELETE

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| type |  |  |
| imageName |  |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
|  | string |  |  |  |

**Response Demo:**

```json

```





## StudentBusinessController

StudentBusinessController


---
### getUnfinishedPractices

> BASIC

**Path:** /api/student/{id}/get-unfinished-practice-list

**Method:** GET

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| id |  |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |
| data | array | NO |  |  |
| &ensp;&ensp;&#124;─ | object |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─practiceId | integer | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─practiceName | string | NO |  |  |

**Response Demo:**

```json
{
  "message": "",
  "data": [
    {
      "practiceId": 0,
      "practiceName": ""
    }
  ]
}
```




---
### getFinishedPractices

> BASIC

**Path:** /api/student/{id}/get-finished-practice-list

**Method:** GET

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| id |  |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |
| data | array | NO |  |  |
| &ensp;&ensp;&#124;─ | object |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─practiceId | integer | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─practiceName | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─practiceTime | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─totalScore | number | NO |  |  |

**Response Demo:**

```json
{
  "message": "",
  "data": [
    {
      "practiceId": 0,
      "practiceName": "",
      "practiceTime": "",
      "totalScore": 0.0
    }
  ]
}
```




---
### getKnowledgePoints

> BASIC

**Path:** /api/student/{id}/practice/get-knowledge-points

**Method:** GET

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| id |  |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |
| data | array | NO |  |  |
| &ensp;&ensp;&#124;─ | object |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─id | integer | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─name | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─description | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─type | string | NO |  |  |

**Response Demo:**

```json
{
  "message": "",
  "data": [
    {
      "id": 0,
      "name": "",
      "description": "",
      "type": ""
    }
  ]
}
```




---
### generatePracticeDefine

> BASIC

**Path:** /api/student/{id}/practice/generate-define

**Method:** POST

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| id |  |  |

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| Content-Type | application/json | YES |  |

**Request Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| name | string | NO |  |  |
| knowledgePoints | array | NO |  |  |
| &ensp;&ensp;&#124;─ | object |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─knowledgePointId | integer | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─num | integer | NO |  |  |
| questionBodyTypes | array | NO |  |  |
| &ensp;&ensp;&#124;─ | object |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─type | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─num | integer | NO |  |  |

**Request Demo:**

```json
{
  "name": "",
  "knowledgePoints": [
    {
      "knowledgePointId": 0,
      "num": 0
    }
  ],
  "questionBodyTypes": [
    {
      "type": "",
      "num": 0
    }
  ]
}
```



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| practiceId | integer | NO |  |  |
| message | string | NO |  |  |
| data | array | NO |  |  |
| &ensp;&ensp;&#124;─ | object |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─practiceQuestionId | integer | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─questionBody | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─questionContent | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─type | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─questionOptions | array | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─ | string |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─sequence | string | NO |  |  |

**Response Demo:**

```json
{
  "practiceId": 0,
  "message": "",
  "data": [
    {
      "practiceQuestionId": 0,
      "questionBody": "",
      "questionContent": "",
      "type": "",
      "questionOptions": [
        ""
      ],
      "sequence": ""
    }
  ]
}
```




---
### generatePracticeAuto

> BASIC

**Path:** /api/student/{id}/practice/generate-auto

**Method:** POST

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| id |  |  |

**Query:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| practiceName |  | YES |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| practiceId | integer | NO |  |  |
| message | string | NO |  |  |
| data | array | NO |  |  |
| &ensp;&ensp;&#124;─ | object |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─practiceQuestionId | integer | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─questionBody | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─questionContent | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─type | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─questionOptions | array | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─ | string |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─sequence | string | NO |  |  |

**Response Demo:**

```json
{
  "practiceId": 0,
  "message": "",
  "data": [
    {
      "practiceQuestionId": 0,
      "questionBody": "",
      "questionContent": "",
      "type": "",
      "questionOptions": [
        ""
      ],
      "sequence": ""
    }
  ]
}
```




---
### saveAnswer

> BASIC

**Path:** /api/student/{id}/practice/save

**Method:** POST

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| id |  |  |

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| Content-Type | application/json | YES |  |

**Request Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| data | array | NO |  |  |
| &ensp;&ensp;&#124;─ | object |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─practiceQuestionId | integer | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─answerContent | string | NO |  |  |

**Request Demo:**

```json
{
  "data": [
    {
      "practiceQuestionId": 0,
      "answerContent": ""
    }
  ]
}
```



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |

**Response Demo:**

```json
{
  "message": ""
}
```




---
### continuePractice

> BASIC

**Path:** /api/student/{id}/continue-practice

**Method:** POST

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| id |  |  |

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| Content-Type | application/json | YES |  |

**Request Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| practiceId | integer | NO |  |  |

**Request Demo:**

```json
{
  "practiceId": 0
}
```



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |
| data | array | NO |  |  |
| &ensp;&ensp;&#124;─ | object |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─practiceQuestionId | integer | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─sequence | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─questionBody | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─questionContent | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─questionType | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─questionOptions | array | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─ | string |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─answerContent | string | NO |  |  |

**Response Demo:**

```json
{
  "message": "",
  "data": [
    {
      "practiceQuestionId": 0,
      "sequence": "",
      "questionBody": "",
      "questionContent": "",
      "questionType": "",
      "questionOptions": [
        ""
      ],
      "answerContent": ""
    }
  ]
}
```




---
### completePractice

> BASIC

**Path:** /api/student/{id}/practice/complete

**Method:** POST

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| id |  |  |

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| Content-Type | application/json | YES |  |

**Request Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| data | array | NO |  |  |
| &ensp;&ensp;&#124;─ | object |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─practiceQuestionId | integer | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─answerContent | string | NO |  |  |

**Request Demo:**

```json
{
  "data": [
    {
      "practiceQuestionId": 0,
      "answerContent": ""
    }
  ]
}
```



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |
| score | number | NO |  |  |

**Response Demo:**

```json
{
  "message": "",
  "score": 0.0
}
```




---
### getAnswer

> BASIC

**Path:** /api/student/{id}/practice/get-answer

**Method:** GET

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| id |  |  |

**Query:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| practiceId |  | YES |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |
| totalScore | number | NO |  |  |
| data | array | NO |  |  |
| &ensp;&ensp;&#124;─ | object |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─sequence | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─questionBody | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─questionContent | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─questionType | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─questionOptions | array | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─ | string |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─answer | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─analysis | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─studentAnswer | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─score | number | NO |  |  |

**Response Demo:**

```json
{
  "message": "",
  "totalScore": 0.0,
  "data": [
    {
      "sequence": "",
      "questionBody": "",
      "questionContent": "",
      "questionType": "",
      "questionOptions": [
        ""
      ],
      "answer": "",
      "analysis": "",
      "studentAnswer": "",
      "score": 0.0
    }
  ]
}
```




---
### deletePractice

> BASIC

**Path:** /api/student/{id}/delete-practice

**Method:** DELETE

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| id |  |  |

**Query:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| practiceId |  | YES |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |

**Response Demo:**

```json
{
  "message": ""
}
```




---
### getAvgScore

> BASIC

**Path:** /api/student/{id}/get-avg-score

**Method:** GET

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| id |  |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |
| data | object | NO |  |  |
| &ensp;&ensp;&#124;─averageHomeworkScore | number | NO |  |  |
| &ensp;&ensp;&#124;─classRank | integer | NO |  |  |

**Response Demo:**

```json
{
  "message": "",
  "data": {
    "averageHomeworkScore": 0.0,
    "classRank": 0
  }
}
```




---
### getMultidimensionalScores

> BASIC

**Path:** /api/student/{id}/get-multidimensional-scores

**Method:** GET

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| id |  |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |
| data | array | NO |  |  |
| &ensp;&ensp;&#124;─ | object |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─name | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─score | number | NO |  |  |

**Response Demo:**

```json
{
  "message": "",
  "data": [
    {
      "name": "",
      "score": 0.0
    }
  ]
}
```




---
### getWeaknessScores

> BASIC

**Path:** /api/student/{id}/get-weakness-scores

**Method:** GET

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| id |  |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |
| data | array | NO |  |  |
| &ensp;&ensp;&#124;─ | object |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─type | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─weaknessName | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─weaknessScore | number | NO |  |  |

**Response Demo:**

```json
{
  "message": "",
  "data": [
    {
      "type": "",
      "weaknessName": "",
      "weaknessScore": 0.0
    }
  ]
}
```




---
### getHistoryScores

> BASIC

**Path:** /api/student/{id}/score-fluctuations

**Method:** GET

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| id |  |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |
| data | array | NO |  |  |
| &ensp;&ensp;&#124;─ | object |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─date | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─score | integer | NO |  |  |

**Response Demo:**

```json
{
  "message": "",
  "data": [
    {
      "date": "",
      "score": 0
    }
  ]
}
```




---
### getUnfinishedAssignments

> BASIC

**Path:** /api/student/{id}/get-unfinished-assignment-list

**Method:** GET

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| id |  |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |
| data | array | NO |  |  |
| &ensp;&ensp;&#124;─ | object |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─assignmentId | integer | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─title | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─description | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─startTime | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─endTime | string | NO |  |  |

**Response Demo:**

```json
{
  "message": "",
  "data": [
    {
      "assignmentId": 0,
      "title": "",
      "description": "",
      "startTime": "",
      "endTime": ""
    }
  ]
}
```




---
### getFinishedAssignments

> BASIC

**Path:** /api/student/{id}/get-finished-assignment-list

**Method:** GET

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| id |  |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |
| data | array | NO |  |  |
| &ensp;&ensp;&#124;─ | object |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─assignmentId | integer | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─title | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─description | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─startTime | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─endTime | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─totalScore | number | NO |  |  |

**Response Demo:**

```json
{
  "message": "",
  "data": [
    {
      "assignmentId": 0,
      "title": "",
      "description": "",
      "startTime": "",
      "endTime": "",
      "totalScore": 0.0
    }
  ]
}
```




---
### getHomeworkDetail

> BASIC

**Path:** /api/student/{id}/homework/get-detail

**Method:** GET

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| id |  |  |

**Query:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| assignmentId |  | YES |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |
| data | array | NO |  |  |
| &ensp;&ensp;&#124;─ | object |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─submissionAnswerId | integer | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─sequence | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─body | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─questionContent | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─questionType | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─questionOptions | array | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─ | string |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─answerContent | string | NO |  |  |

**Response Demo:**

```json
{
  "message": "",
  "data": [
    {
      "submissionAnswerId": 0,
      "sequence": "",
      "body": "",
      "questionContent": "",
      "questionType": "",
      "questionOptions": [
        ""
      ],
      "answerContent": ""
    }
  ]
}
```




---
### completeHomework

> BASIC

**Path:** /api/student/{id}/homework/complete

**Method:** POST

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| id |  |  |

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| Content-Type | application/json | YES |  |

**Request Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| data | array | NO |  |  |
| &ensp;&ensp;&#124;─ | object |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─submissionAnswerId | integer | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─answerContent | string | NO |  |  |

**Request Demo:**

```json
{
  "data": [
    {
      "submissionAnswerId": 0,
      "answerContent": ""
    }
  ]
}
```



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |

**Response Demo:**

```json
{
  "message": ""
}
```




---
### getHomeworkAnswer

> BASIC

**Path:** /api/student/{id}/homework/get-answer

**Method:** GET

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| id |  |  |

**Query:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| assignmentId |  | YES |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |
| totalScore | integer | NO |  |  |
| feedback | string | NO |  |  |
| data | array | NO |  |  |
| &ensp;&ensp;&#124;─ | object |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─sequence | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─body | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─questionContent | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─questionType | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─questionOptions | array | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─ | string |  |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─answer | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─analysis | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─studentAnswer | string | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─score | integer | NO |  |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─feedback | string | NO |  |  |

**Response Demo:**

```json
{
  "message": "",
  "totalScore": 0,
  "feedback": "",
  "data": [
    {
      "sequence": "",
      "body": "",
      "questionContent": "",
      "questionType": "",
      "questionOptions": [
        ""
      ],
      "answer": "",
      "analysis": "",
      "studentAnswer": "",
      "score": 0,
      "feedback": ""
    }
  ]
}
```





## FindPasswordController

FindPasswordController


---
### sendCode

> BASIC

**Path:** /api/find-password/send-code

**Method:** POST

> REQUEST

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| Content-Type | application/json | YES |  |

**Request Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| email | string | NO |  |  |
| type | string | NO |  |  |

**Request Demo:**

```json
{
  "email": "",
  "type": ""
}
```



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |

**Response Demo:**

```json
{
  "message": ""
}
```




---
### resetPassword

> BASIC

**Path:** /api/find-password/reset-password

**Method:** POST

> REQUEST

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| Content-Type | application/json | YES |  |

**Request Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| type | string | NO |  |  |
| email | string | NO |  |  |
| code | string | NO |  |  |
| password | string | NO |  |  |

**Request Demo:**

```json
{
  "type": "",
  "email": "",
  "code": "",
  "password": ""
}
```



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | required | default | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| message | string | NO |  |  |

**Response Demo:**

```json
{
  "message": ""
}
```





