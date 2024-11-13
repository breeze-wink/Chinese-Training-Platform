# 接口文档

## Student 

### Login `finished`

- **接口路径**：`/api/student/login` 
- **请求方法**：POST
- **接口说明**：学生用户通过账号和密码进行登录。
- **请求说明**
  - 请求头: `Content-Type` : `application/json`
  - 请求参数:
    - 请求体(`JSON` 格式)：
    ```json
    {
      "account" : "string", // 用户名或邮箱
      "password" : "string" // 密码
    }
    ```
- **响应说明**
  - 响应格式: `JSON`
  - **成功响应** (`200 OK`):
    ```json
    {
      "message" : "success",
      "id" : 12345
    }
    ```
  - **失败响应** (`401 Unauthorized`):
    ```json
    {
      "message" : "用户名或密码错误",
      "id" : null
    }
    ```

### Send Verification Code `finished`

- **接口路径**：`/api/student/send-verification`
- **请求方法**：POST
- **接口说明**：学生用户通过邮箱发送验证码(`邮箱合法性在前端检查`)
- **请求说明**
  
  - 请求头: `Content-Type` : `application/json`
  - 请求参数:
    - 请求体(`JSON` 格式)：
    ```json
    {
      "email" : "string" // 学生的邮箱地址
    }
    ```
- **响应说明**
  - 响应格式: `JSON`
  - **成功响应** (`200 OK`):
    ```json
    {
      "message" : "验证码已发送",
      "verificationCode" : "string"
    }
    ```
  - **失败响应** (`400 Bad Request`):
    ```json
    {
      "message" : "string", // 邮箱已注册
      "verificationCode" : null
    }
    ```

### Register `finished`

- **接口路径**：`/api/student/register`
- **请求方法**：POST
- **接口说明**：学生用户通过邮箱和验证码进行注册。
- **请求说明**
  - 请求头: `Content-Type` : `application/json`
  - 请求参数:
    - 请求体(`JSON` 格式)：
    ```json
    {
      "email" : "string", // 学生的邮箱地址
      "username" : "string",
      "password" : "string", // 密码
      "confirmPassword" : "string"
    }
    ```
- **响应说明**
  - 响应格式: `JSON`
  - **成功响应** (`200 OK`):
    
    ```json
    {
      "message" : "注册成功",
      "id" : 12345
    }
    ```
  - **失败响应** (`400 Bad Request`):
    ```json
    {
      "message" : "验证码错误或已过期",
      "id" : null
    }
    ```

### Get Student Info `finished`

- **接口路径**：`/api/student/{id}`
- **请求方法**：GET
- **接口说明**：学生用户通过用户 ID 获取自己的信息。
- **请求说明**：
  - 请求参数：
    - 路径参数（Path Variable）：`id` - 用户的唯一标识符

- **响应说明**：
  - 响应格式：`JSON`
  - **成功响应** (`200 OK`):
    ```json
    {
      "message": "success",
      "data": {
        "username": "string",
        "email": "string",
        "name": "string",
        "grade": 12,
        "schoolName": "string"
      }
    }
    ```
  - **失败响应** (`404 Not Found`):
    ```json
    {
      "message": "用户未找到",
      "data": null
    }
    ```

## Teacher

### Login `finished`

- **接口路径**：`/api/teacher/login`
- **请求方法**：POST
- **接口说明**：教师用户通过账号和密码进行登录。
- **请求说明**
  - 请求头: `Content-Type` : `application/json`
  - 请求参数:
    - 请求体(`JSON` 格式)：
    ```json
    {
      "account" : "string", // 用户名或邮箱
      "password" : "string" // 密码
    }
    ```
- **响应说明**
  - 响应格式: `JSON`
  - **成功响应** (`200 OK`):
    ```json
    {
      "message" : "success",
      "id" : 12345
    }
    ```
  - **失败响应** (`401 Unauthorized`):
    ```json
    {
      "message" : "用户名或密码错误",
      "id" : null
    }
    ```

### Send Verification Code `finished`

- **接口路径**：`/api/teacher/send-verification`
- **请求方法**：POST
- **接口说明**：教师用户通过邮箱发送验证码。
- **请求说明**
  - 请求头: `Content-Type` : `application/json`
  - 请求参数:
    - 请求体(`JSON` 格式)：
    ```json
    {
      "authorizationCode": "string", // 授权码
      "email" : "string" // 教师的邮箱地址
    }
    ```
- **响应说明**
  - 响应格式: `JSON`
  - **成功响应** (`200 OK`):
    ```json
    {
      "message" : "验证码已发送",
      "verificationCode" : "string",
      "schoolId": 12345
    }
    ```
  - **失败响应** (`400 Bad Request`):
    ```json
    {
      "message" : "String", // 授权码不可用 or 邮箱已注册 
      "verificationCode" : null,
      "schoolId": null
    }
    ```

### Register `finished`

- **接口路径**：`/api/teacher/register`
- **请求方法**：POST
- **接口说明**：教师用户通过邮箱、密码进行注册。
- **请求说明**
  
  - 请求头: `Content-Type` : `application/json`
  - 请求参数:
    - 请求体(`JSON` 格式)：
    ```json
    {
      "email" : "string", // 教师的邮箱地址
      "password" : "string", // 密码
      "confirmPassword" : "string",
      "schoolId" : 12345
    }
    ```
- **响应说明**
  - 响应格式: `JSON`
  - **成功响应** (`200 OK`):
    
    ```json
    {
      "message" : "注册成功",
      "id" : 12345
    }
    ```
  - **失败响应** (`400 Bad Request`):
    ```json
    {
      "message" : "密码输入不一致",
      "id" : null
    }
    ```
    

### Get Teacher Info `finished`

- **接口路径**：`/api/teacher/{id}`
- **请求方法**：GET
- **接口说明**：教师用户通过用户 ID 获取自己的信息。
- **请求说明**：
  - 请求参数：
    - 路径参数（Path Variable）：`id` - 用户的唯一标识符

- **响应说明**：
  - 响应格式：`JSON`
  - **成功响应** (`200 OK`):
    ```json
    {
      "message" : "success",
      "data": {
          "name": "string",
          "email": "string",
          "phoneNumber": "string",
          "schoolName": "string"
      }
    }
    ```
  - **失败响应** (`404 Not Found`):
    
    ```json
    {
      "message": "用户未找到",
      "data" : null
    }
    ```
### Update Username 

- **接口路径**：`/api/teacher/{teacherId}/updateUsername`
- **请求方法**：POST
- **接口说明**：教师用户修改自己的用户名。
- **请求说明**：
  - 请求参数：
    - 路径参数（Path Variable）：`teacherId` - 教师的唯一标识符
    - 请求体(`JSON` 格式)：
    ```json
    {
      "username": "newUsername"
    }
    ```
    - `username`：教师希望修改的新用户名。

- **响应说明**：
  - 响应格式：`JSON`
  - **成功响应** (`200 OK`):
    ```json
    {
      "message": "用户名修改成功",
    }
    ```
  - **失败响应** (`400 Bad Request` 或 `409 Conflict`):
    ```json
    {
      "message": "用户名修改失败，用户名已存在或其他错误",
    }
    ```

### Update Phone Number 

- **接口路径**：`/api/teacher/{teacherId}/updatePhoneNumber`
- **请求方法**：POST
- **接口说明**：教师用户修改自己的手机号。
- **请求说明**：
  - 请求参数：
    - 路径参数（Path Variable）：`teacherId` - 教师的唯一标识符
    - 请求体(`JSON` 格式)：
    ```json
    {
      "phoneNumber": "newPhoneNumber"
    }
    ```
    - `phoneNumber`：教师希望修改的新手机号。

- **响应说明**：
  - 响应格式：`JSON`
  - **成功响应** (`200 OK`):
    ```json
    {
      "message": "手机号修改成功",
    }
    ```
  - **失败响应** (`400 Bad Request` 或 `409 Conflict`):
    ```json
    {
      "message": "手机号修改失败，手机号已存在或格式错误",
    }
    ```

### View Curriculum Standard `finished`

- **接口路径**：`/api/teacher/{teacherId}/viewCurriculumStandard`
- **请求方法**：GET
- **接口说明**：教师用户查看课程标准（课标），课标内容为一篇文章，分点列出。
- **请求说明**：
  - 请求参数：
    - 路径参数（Path Variable）：`teacherId` - 教师的唯一标识符
  - 请求体：无

- **响应说明**：
  - 响应格式：`JSON`
  - **成功响应** (`200 OK`):
    ```json
    {
      "message": "课标获取成功",
      "curriculumStandard": [
        {
          "point": "第一部分：基本要求",
          "description": "在此部分，学生应掌握的基本知识点包括数学基础、物理实验技能等。"
        },
        {
          "point": "第二部分：教学目标",
          "description": "明确学生应达到的学业目标，如提高问题解决能力、培养批判性思维等。"
        },
        {
          "point": "第三部分：课程内容",
          "description": "课程内容涉及到的知识点包括：1. 代数基础；2. 函数的应用等。"
        }
      ]
    }
    ```
  - **失败响应** (`404 Not Found` 或 `500 Internal Server Error`):
    ```json
    {
      "message": "课标获取失败，未找到相关课标信息",
      "curriculumStandard": null
    }
    ```

### Create Class `finished`

- **接口路径**：`/api/teacher/{id}/createClass`
- **请求方法**：POST
- **接口说明**：教师用户创建新班级，传输班级名称和班级描述，获得班级码。
- **请求说明**：
  - 请求参数：
    - 路径参数（Path Variable）：`id` - 用户的唯一标识符
    - 请求体(`JSON` 格式)：
    ```json
    {
      "className" : "string", // 班级名称
      "classDescription" : "string" // 班级描述
      
    }
    ```

- **响应说明**：
  - 响应格式：`JSON`
  - **成功响应** (`200 OK`):
    ```json
    {
      "message" : "班级创建成功",
      "data": {
          "classCode": "string",
      }
    }
    ```
  - **失败响应** (`400 Bad Request`):

    ```json
    {
      "message": "班级创建失败",
      "data" : null
    }
    ```


### Create Group `finished`

- **接口路径**：`/api/teacher/{id}/createGroup`
- **请求方法**：POST
- **接口说明**：教师用户为指定班级创建一个小组，并指定小组成员（学生ID数组）。
  - **请求说明**：
    - 请求参数：
      - 路径参数（Path Variable）：`id` - 用户的唯一标识符
      - 请求体(`JSON` 格式)：
      ```json
      {
         "classId": "string", // 所属班级的唯一标识符
         "groupName": "string", // 小组名称
         "studentIds": ["string", "string", "string"], // 学生ID的列表
         "groupDescription" : "string" // 班级描述
      
      }
      ```

- **响应说明**：
  - 响应格式：`JSON`
  - **成功响应** (`200 OK`):
  
    ```json
    {
      "message" : "小组创建成功",
    }
    ```
  - **失败响应** (`400 Bad Request`):

    ```json
    {
      "message": "小组创建失败",
    }
    ```

### Get Classes Information `finished`

- **接口路径**：`/api/teacher/{id}/getClasses`
- **请求方法**：GET
- **接口说明**：教师用户获取其班级的信息。
- **请求说明**：
  - 请求参数：
    - 路径参数（Path Variable）：`id` - 用户的唯一标识符
  - 请求体：无

- **响应说明**：
  - 响应格式：`JSON`
  - **成功响应** (`200 OK`):
    ```json
    {
      "message": "班级信息获取成功",
      "data": [
        {
          "classId": "3425342", // 班级ID
          "classCode": "ABC123", // 班级码
          "className": "班级名称", // 班级名称
          "classDescription": "班级描述" // 班级描述
        },
        {
          "classId": "3425342", // 班级ID
          "classCode": "XYZ456",
          "className": "班级名称2",
          "classDescription": "班级描述2"
        }
      ]
    }
    ```
  - **失败响应** (`400 Bad Request`):
    ```json
    {
      "message": "获取班级信息失败",
      "data": null
    }
    ```

---

### Get Groups Information `finished`

- **接口路径**：`/api/teacher/{id}/getGroups`
- **请求方法**：GET
- **接口说明**：教师用户获取其小组的信息。
- **请求说明**：
  - 请求参数：
    - 路径参数（Path Variable）：`id` - 用户的唯一标识符
  - 请求体：无

- **响应说明**：
  - 响应格式：`JSON`
  - **成功响应** (`200 OK`):
    ```json
    {
      "message": "小组信息获取成功",
      "data": [
        {
          "groupId": "31243", // 小组ID
          "groupName": "小组1", // 小组名称
          "className": "软件2205", // 所属班级名称
          "groupDescription": "小组描述1" // 小组描述
        },
        {
          "groupId": "31243", // 小组ID
          "groupName": "小组2",
          "classCode": "ABC123",
          "groupDescription": "小组描述2"
        }
      ]
    }
    ```
  - **失败响应** (`400 Bad Request`):
    ```json
    {
      "message": "获取小组信息失败",
      "data": null
    }
    ```
### Get Class Members Information `finished`

- **接口路径**：`/api/teacher/{id}/getClassMembers`
- **请求方法**：GET
- **接口说明**：教师用户获取某个班级的学生信息，包括学生姓名和学生ID。
- **请求说明**：
  - 请求参数：
    - 路径参数（Path Variable）：`id` - 教师的唯一标识符
    - 查询参数（Query Parameter）：`classId` - 班级的唯一标识符
  - 请求体：无

- **响应说明**：
  - 响应格式：`JSON`
  - **成功响应** (`200 OK`):
    ```json
    {
      "message": "班级成员信息获取成功",
      "data": [
        {
          "studentName": "张三", // 学生姓名
          "studentId": "123456" // 学生ID
        },
        {
          "studentName": "李四",
          "studentId": "789012"
        }
      ]
    }
    ```
  - **失败响应** (`400 Bad Request`):
    ```json
    {
      "message": "获取班级成员信息失败",
      "data": null
    }
    ```


### Get Student's Average Homework Score and Class Rank `finished`

- **接口路径**：`/api/teacher/{id}/getStudentAverageHomework`
- **请求方法**：GET
- **接口说明**：教师用户获取某个学生的作业平均分和班级排名。
- **请求说明**：
  - 请求参数：
    - 路径参数（Path Variable）：`id` - 教师的唯一标识符
    - 查询参数（Query Parameter）：`studentId` - 学生的唯一标识符
  - 请求体：无

- **响应说明**：
  - 响应格式：`JSON`
  - **成功响应** (`200 OK`):
    ```json
    {
      "message": "学生作业平均分和班级排名获取成功",
      "data": {
        "averageHomeworkScore": 85.5, // 作业平均分
        "classRank": 10 // 班级排名
      }
    }
    ```
  - **失败响应** (`400 Bad Request`):
    ```json
    {
      "message": "获取作业平均分和班级排名失败",
      "data": null
    }
    ```

### Get Student's Five Dimensional Scores `finished`

- **接口路径**：`/api/teacher/{id}/getStudentFiveDimensionalScores`
- **请求方法**：GET
- **接口说明**：教师用户获取某个学生的五维数据得分率。
- **请求说明**：
  - 请求参数：
    - 路径参数（Path Variable）：`id` - 教师的唯一标识符
    - 查询参数（Query Parameter）：`studentId` - 学生的唯一标识符
  - 请求体：无

- **响应说明**：
  - 响应格式：`JSON`
  - **成功响应** (`200 OK`):
    ```json
    {
      "message": "五维数据得分率获取成功",
      "data": {
        "accumulationAndApplication": 92, // 积累与运用得分率
        "composition": 85, // 作文得分率
        "classicReading": 88, // 名著阅读得分率
        "ancientPoetryReading": 90, // 古诗文阅读得分率
        "modernReading": 87 // 现代文阅读得分率
      }
    }
    ```
  - **失败响应** (`400 Bad Request`):
    ```json
    {
      "message": "获取五维数据得分率失败",
      "data": null
    }
    ```

### Get Student's Weakness Scores `finished`

- **接口路径**：`/api/teacher/{id}/getStudentWeaknessScores`
- **请求方法**：GET
- **接口说明**：教师用户获取某个学生的短板得分，包括短板名称和得分率。
- **请求说明**：
  - 请求参数：
    - 路径参数（Path Variable）：`id` - 教师的唯一标识符
    - 查询参数（Query Parameter）：`studentId` - 学生的唯一标识符
  - 请求体：无

- **响应说明**：
  - 响应格式：`JSON`
  - **成功响应** (`200 OK`):
    ```json
    {
      "message": "学生短板得分获取成功",
      "data": [
        {
          "weaknessName": "积累与运用", // 短板名称
          "weaknessScore": 75 // 短板得分率
        },
        {
          "weaknessName": "现代文阅读",
          "weaknessScore": 78
        },
        {
          "weaknessName": "作文",
          "weaknessScore": 70
        }
      ]
    }
    ```
  - **失败响应** (`400 Bad Request`):
    ```json
    {
      "message": "获取学生短板得分失败",
      "data": null
    }
    ```


### Get Student's Historical Homework Scores `finished`

- **接口路径**：`/api/teacher/{id}/getStudentHistoricalHomeworkScores`
- **请求方法**：GET
- **接口说明**：教师用户获取某个学生的历史作业得分率列表。
- **请求说明**：
  - 请求参数：
    - 路径参数（Path Variable）：`id` - 教师的唯一标识符
    - 查询参数（Query Parameter）：`studentId` - 学生的唯一标识符
  - 请求体：无

- **响应说明**：
  - 响应格式：`JSON`
  - **成功响应** (`200 OK`):
    ```json
    {
      "message": "学生历史作业得分率获取成功",
      "data": [90, 85, 88, 92, 80] // 历史作业得分率的列表
    }
    ```
  - **失败响应** (`400 Bad Request`):
    ```json
    {
      "message": "获取学生历史作业得分率失败",
      "data": null
    }
    ```

### Get Class's Historical Average Score `finished`

- **接口路径**：`/api/teacher/{teacherId}/classes/{classId}/historicalAverageScores`
- **请求方法**：GET
- **接口说明**：教师用户获取某个班级的历史平均分列表。
- **请求说明**：
  - 请求参数：
    - 路径参数（Path Variable）：`teacherId` - 教师的唯一标识符
    - 路径参数（Path Variable）：`classId` - 班级的唯一标识符
  - 请求体：无

- **响应说明**：
  - 响应格式：`JSON`
  - **成功响应** (`200 OK`):
    ```json
    {
      "message": "班级历史平均分获取成功",
      "data": [
        {
          "score": 85.0,
          "deadline": "2024-10-20T23:59:59"
        },
        {
          "score": 90.5,
          "deadline": "2024-09-15T23:59:59"
        }
      ]
    }
    ```
  - **失败响应** (`400 Bad Request`):
    ```json
    {
      "message": "获取班级历史平均分失败",
      "data": null
    }
    ```
### Disband Class `finished`

- **接口路径**：`/api/teacher/{teacherId}/classes/{classId}/disband`
- **请求方法**：DELETE
- **接口说明**：教师用户解散某个班级。
- **请求说明**：
  - 请求参数：
    - 路径参数（Path Variable）：`teacherId` - 教师的唯一标识符
    - 路径参数（Path Variable）：`classId` - 班级的唯一标识符
  - 请求体：无

- **响应说明**：
  - 响应格式：`JSON`
  - **成功响应** (`200 OK`):
    ```json
    {
      "message": "班级解散成功",
    }
    ```
  - **失败响应** (`400 Bad Request`):
    ```json
    {
      "message": "班级解散失败",
    }
    ```


### Remove Student from Class `finished`

- **接口路径**：`/api/teacher/{teacherId}/classes/{classId}/students/{studentId}/remove`
- **请求方法**：DELETE
- **接口说明**：教师用户将某个学生移出指定班级。
- **请求说明**：
  - 请求参数：
    - 路径参数（Path Variable）：`teacherId` - 教师的唯一标识符
    - 路径参数（Path Variable）：`classId` - 班级的唯一标识符
    - 路径参数（Path Variable）：`studentId` - 学生的唯一标识符
  - 请求体：无

- **响应说明**：
  - 响应格式：`JSON`
  - **成功响应** (`200 OK`):
    ```json
    {
      "message": "学生移出班级成功",
    }
    ```
  - **失败响应** (`400 Bad Request`):
    ```json
    {
      "message": "学生移出班级失败",
    }
    ```

### Get Group Members `finished`

- **接口路径**：`/api/teacher/{teacherId}/classes/{classId}/groups/{groupId}/members`
- **请求方法**：GET
- **接口说明**：教师用户查看某个小组的成员构成。
- **请求说明**：
  - 请求参数：
    - 路径参数（Path Variable）：`teacherId` - 教师的唯一标识符
    - 路径参数（Path Variable）：`classId` - 班级的唯一标识符
    - 路径参数（Path Variable）：`groupId` - 小组的唯一标识符
  - 请求体：无

- **响应说明**：
  - 响应格式：`JSON`
  - **成功响应** (`200 OK`):
    ```json
    {
      "message": "小组成员构成获取成功",
      "data": [
        {
          "studentId": "12345",
          "studentName": "张三"
        },
        {
          "studentId": "67890",
          "studentName": "李四"
        }
      ]
    }
    ```
  - **失败响应** (`400 Bad Request`):
    ```json
    {
      "message": "获取小组成员构成失败",
      "data": null
    }
    ```

### Get Group's Historical Scores `finished`

- **接口路径**：`/api/teacher/{teacherId}/classes/{classId}/groups/{groupId}/historicalScores`
- **请求方法**：GET
- **接口说明**：教师用户查看某个小组的历史得分率，并包括当次作业的截止时间。
- **请求说明**：
  - 请求参数：
    - 路径参数（Path Variable）：`teacherId` - 教师的唯一标识符
    - 路径参数（Path Variable）：`classId` - 班级的唯一标识符
    - 路径参数（Path Variable）：`groupId` - 小组的唯一标识符
  - 请求体：无

- **响应说明**：
  - 响应格式：`JSON`
  - **成功响应** (`200 OK`):
    ```json
    {
      "message": "小组历史得分率获取成功",
      "data": [
        {
          "score": 85.0,
          "deadline": "2024-10-20T23:59:59"
        },
        {
          "score": 90.5,
          "deadline": "2024-09-15T23:59:59"
        }
      ]
    }
    ```
  - **失败响应** (`400 Bad Request`):
    ```json
    {
      "message": "获取小组历史得分率失败",
    }
    ```

### Disband Group `finished`

- **接口路径**：`/api/teacher/{teacherId}/classes/{classId}/groups/{groupId}/disband`
- **请求方法**：DELETE
- **接口说明**：教师用户解散某个小组。
- **请求说明**：
  - 请求参数：
    - 路径参数（Path Variable）：`teacherId` - 教师的唯一标识符
    - 路径参数（Path Variable）：`classId` - 班级的唯一标识符
    - 路径参数（Path Variable）：`groupId` - 小组的唯一标识符
  - 请求体：无

- **响应说明**：
  - 响应格式：`JSON`
  - **成功响应** (`200 OK`):
    ```json
    {
      "message": "小组解散成功",
    }
    ```
  - **失败响应** (`400 Bad Request`):
    ```json
    {
      "message": "小组解散失败",
    }
    ```

### Remove Student from Group `finished`

- **接口路径**：`/api/teacher/{teacherId}/classes/{classId}/groups/{groupId}/students/{studentId}/remove`
- **请求方法**：DELETE
- **接口说明**：教师用户将某个学生移出指定小组。
- **请求说明**：
  - 请求参数：
    - 路径参数（Path Variable）：`teacherId` - 教师的唯一标识符
    - 路径参数（Path Variable）：`classId` - 班级的唯一标识符
    - 路径参数（Path Variable）：`groupId` - 小组的唯一标识符
    - 路径参数（Path Variable）：`studentId` - 学生的唯一标识符
  - 请求体：无

- **响应说明**：
  - 响应格式：`JSON`
  - **成功响应** (`200 OK`):
    ```json
    {
      "message": "学生移出小组成功",
    }
    ```
  - **失败响应** (`400 Bad Request`):
    ```json
    {
      "message": "学生移出小组失败",
    }
    ```

### Generate Exam Paper `finished`

- **接口路径**：`/api/teacher/{teacherId}/generateExam`
- **请求方法**：GET
- **接口说明**：教师用户自动生成试卷，返回题目信息。
- **请求说明**：
  - 请求参数：
    - 路径参数（Path Variable）：`teacherId` - 教师的唯一标识符
  - 请求体：无

- **响应说明**：
  - 响应格式：`JSON`
  - **成功响应** (`200 OK`):
    ```json
    {
      "message": "试卷生成成功",
      "examQuestions": [
        {
          "question": "1 + 1 = ?",
          "type": "单选题",
          "answer": "2",
          "options": [
            "1",
            "2",
            "3",
            "4"
          ]
        },
        {
          "question": "《西游记》的作者是谁？",
          "type": "单选题",
          "answer": "吴承恩",
          "options": [
            "李白",
            "唐婉",
            "吴承恩",
            "孔子"
          ]
        },
        {
          "question": "请简述计算机网络的基本概念。",
          "type": "简答题",
          "answer": "计算机网络是指由多台计算机及其连接设备组成的系统，通过通信介质传输数据。"
        }
      ]
    }
    ```
  - **失败响应** (`400 Bad Request`):
    ```json
    {
      "message": "试卷生成失败",
      "examQuestions": null
    }
    ```

### Generate Custom Exam Paper `finished`

- **接口路径**：`/api/teacher/{teacherId}/generateCustomExam`
- **请求方法**：POST
- **接口说明**：教师用户根据自定义的题目设置信息生成试卷，返回题目信息。
- **请求说明**：
  - 请求参数：
    - 路径参数（Path Variable）：`teacherId` - 教师的唯一标识符
    - 请求体(`JSON` 格式)：
    ```json
    {
      "topics": [
        {
          "topic": "数学",
          "quantity": 5
        },
        {
          "topic": "语文",
          "quantity": 3
        }
      ]
    }
    ```

    - `topics`：题目设置信息的 List，每个元素包含：
      - `topic`：考点名称（如“数学”）。
      - `quantity`：该考点需要的题目数量。

- **响应说明**：
  - 响应格式：`JSON`
  - **成功响应** (`200 OK`):
    ```json
    {
      "message": "试卷生成成功",
      "examQuestions": [
        {
          "question": "1 + 1 = ?",
          "type": "单选题",
          "answer": "2",
          "options": [
            "1",
            "2",
            "3",
            "4"
          ]
        },
        {
          "question": "《西游记》的作者是谁？",
          "type": "单选题",
          "answer": "吴承恩",
          "options": [
            "李白",
            "唐婉",
            "吴承恩",
            "孔子"
          ]
        },
        {
          "question": "请简述计算机网络的基本概念。",
          "type": "简答题",
          "answer": "计算机网络是指由多台计算机及其连接设备组成的系统，通过通信介质传输数据。"
        }
      ]
    }
    ```
  - **失败响应** (`400 Bad Request`):
    ```json
    {
      "message": "试卷生成失败",
      "examQuestions": null
    }
    ```









## SystemAdmin

### Login `finished`

- **接口路径**：`/api/system-admin/login`
- **请求方法**：POST
- **接口说明**：系统管理员用户通过账号和密码进行登录。
- **请求说明**
  - 请求头: `Content-Type` : `application/json`
  - 请求参数:
    - 请求体(`JSON` 格式)：
    ```json
    {
      "account" : "string", // 用户名或邮箱
      "password" : "string" // 密码
    }
    ```
- **响应说明**
  - 响应格式: `JSON`
  - **成功响应** (`200 OK`):
    ```json
    {
      "message" : "success",
      "id" : 12345
    }
    ```
  - **失败响应** (`401 Unauthorized`):
    ```json
    {
      "message" : "用户名或密码错误",
      "id" : null
    }
    ```

### Get SystemAdmin Info `finished`

- **接口路径**：`/api/system-admin/{id}`
- **请求方法**：GET
- **接口说明**：系统管理员用户通过用户 ID 获取自己的信息。
- **请求说明**：
  - 请求参数：
    - 路径参数（Path Variable）：`id` - 用户的唯一标识符

- **响应说明**：
  - 响应格式：`JSON`
  - **成功响应** (`200 OK`):
    ```json
    {
      "message": "success",
      "data": {
          "username": "string",
          "email": "string"
      }
    }
    ```
  - **失败响应** (`404 Not Found`):
    ```json
    {
      "message": "用户未找到",
      "data": null
    }
    ```

## SchoolAdmin

### Login `finished`

- **接口路径**：`/api/school-admin/login`
- **请求方法**：POST
- **接口说明**：学校管理员用户通过用户名和密码进行登录。
- **请求说明**
  - 请求头: `Content-Type` : `application/json`
  - 请求参数:
    - 请求体(`JSON` 格式)：
    ```json
    {
      "account" : "string", // 用户名或邮箱
      "password" : "string" // 密码
    }
    ```
- **响应说明**
  - 响应格式: `JSON`
  - **成功响应** (`200 OK`):
    ```json
    {
      "message" : "success",
      "id" : 12345
    }
    ```
  - **失败响应** (`401 Unauthorized`):
    ```json
    {
      "message" : "用户名或密码错误",
      "id" : null
    }
    ```

### Get SchoolAdmin Info `finished`

- **接口路径**：`/api/school-admin/{id}`
- **请求方法**：GET
- **接口说明**：学校管理员用户通过用户 ID 获取自己的信息。
- **请求说明**：
  - 请求参数：
    - 路径参数（Path Variable）：`id` - 用户的唯一标识符

- **响应说明**：
  - 响应格式：`JSON`
  - **成功响应** (`200 OK`):
    ```json
    {
      "message" : "success",
      "data": {
          "username": "string",
          "email": "string",
          "schoolName": "string"
      }
    }
    ```
  - **失败响应** (`404 Not Found`):
    ```json
    {
      "message": "用户未找到",
      "data": null
    }
    ```
