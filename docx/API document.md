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
        "schoolName": "string",
        "className": "string"
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
    
### Edit Personal Information `finished`
- **接口路径**：`/api/student/{id}/edit-information`
- **请求方法**：`POST`
- **接口说明**：学生用户通过进入编辑，更新个人信息。
- **请求说明**
- 请求头: `Content-Type` : `application/json`
- 请求参数:- 路径参数（Path Variable）：`id` - 用户的唯一标识符
- 请求体(`JSON` 格式)：
```json
{
  "username": "string",
  "name": "string",
  "grade": "int"
}
```
- **响应说明**
  - 响应格式: `JSON`
  - **成功响应** (`200 OK`):
    ```json
    {
      "message": "个人信息更新成功",
      "data": {
         "username": "string",
         "name": "string",
         "grade": "int"
      }
    }
    ```
  - **失败响应** (`400 Bad Request`):
      ```json
      {
        "message": "个人信息更新失败",
        "data": null
     }
     ```

### Change Email `finished`

- **接口路径**：`/api/student/{id}/change-email`
- **请求方法**：`POST`
- **接口说明**：学生用户更换邮箱输入正确验证码后，更换邮箱。
- **请求说明**
- 请求头: `Content-Type` : `application/json`
- 请求参数:- 路径参数（Path Variable）：`id` - 用户的唯一标识符
- 请求体(`JSON` 格式)：
```json
{
  "email": "string"
}
```
- **响应说明**
  
  - 响应格式: `JSON`
  - **成功响应** (`200 OK`):
    ```json
    {
      "message": "邮箱更换成功",
      "data": {
          "email": "string"
         },
    }
    ```
  - **失败响应** (`400 Bad Request`):
    ```json
    {
      "message": "邮箱更换失败",
      "data": null
    }
    ```

### Send Verification Code To Change Student’s Email `finished`
- **接口路径**：`/api/student/{id}/change-email/send-verification`
- **请求方法**：`POST`
- **接口说明**：学生用户点击账号信息管理的更换绑定邮箱中发送验证码。
- **请求说明**
- 请求头: `Content-Type` : `application/json`
- 请求参数:- 路径参数（Path Variable）：`id` - 用户的唯一标识符
- 请求体(`JSON` 格式)：
  ```json
  {
      "email": "string"
  }
  ```
- **响应说明**
  - 响应格式: `JSON`
  - **成功响应** (`200 OK`):
  ```json
  {
    "message": "验证码发送成功",
    "data": {
    "verificationCode" : "string"
    }
  }
  ```
  - **失败响应** (`400 Bad Request`):
  ```json
  {
    "message": "验证码发送失败",
    "data": null
  }
  ```

### Change Password `finished`
- **接口路径**：`/api/student/{id}/change-password`
- **请求方法**：`POST`
- **接口说明**：学生用户点击账号信息管理的修改密码进行修改。
- **请求说明**
- 请求头: `Content-Type` : `application/json`
- 请求参数:- 路径参数（Path Variable）：`id` - 用户的唯一标识符
- 请求体(`JSON` 格式)：
  ```json
  {
    "oldPassword": "string",
    "newPassword": "string"
  }
  ```
- **响应说明**
  - 响应格式: `JSON`
  - **成功响应** (`200 OK`):
  ```json
  {
    "message": "密码更改成功"
  }
  ```
  - **失败响应** (`400 Bad Request`):
  ```json
  {
      "message": "密码更改失败"
  }
  ```

### Account Deactivation `finished`
- **接口路径**：`/api/student/{id}/account-deactivation`
- **请求方法**：`DELETE`
- **接口说明**：学生注销账号。
- **请求说明**
- 请求头: `Content-Type` : `application/json`
- 请求参数:- 路径参数（Path Variable）：`id` - 用户的唯一标识符
- 请求体(`JSON` 格式)：
  无
- **响应说明**
  - 响应格式: `JSON`
  - **成功响应** (`200 OK`):
  ```json
  {
    "message": "账号注销成功"
  }
  ```
  - **失败响应** (`400 Bad Request`):
  ```json
  {
      	"message": "账号注销失败"
  }
  ```

### Join Class `old` `finished`
- **接口路径**：`/api/student/{id}/join-class`
- **请求方法**：`POST`
- **接口说明**：学生加入一个班级。
- **请求说明**
- 请求头: `Content-Type` : `application/json`
- 请求参数:- 路径参数（Path Variable）：`id` - 用户的唯一标识符
- 请求体(`JSON` 格式)：
  ```json
  {
    "inviteCode": "string"
  }
  ```
- **响应说明**
  - 响应格式: `JSON`
  - **成功响应** (`200 OK`):
  ```json
  {
    "message": "成功加入班级",
    "data": {
      "className" : "string",
      "schoolName" : "string",
    }
  }
  ```
  - **失败响应** (`400 Bad Request`):
  ```json
  {
     "message": "加入班级失败",
  	 "data": null
  }
  ```



### Join Class `new` `finished
- **接口路径**：`/api/student/{id}/join-class`
- **请求方法**：`POST`
- **接口说明**：学生加入一个班级。
- **请求说明**
- 请求头: `Content-Type` : `application/json`
- 请求参数:- 路径参数（Path Variable）：`id` - 用户的唯一标识符
- 请求体(`JSON` 格式)：
  ```json
  {
    "inviteCode": "string"
  }
  ```
- **响应说明**
  - 响应格式: `JSON`
  - **成功响应** (`200 OK`):
  ```json
  {
    "message": "成功发送入班申请"
  }
  ```
  - **失败响应** (`400 Bad Request`):
  ```json
  {
     "message": "发送入班申请失败"
  }
  ```




### Get Essay Info `finished`

- **接口路径**：`/api/student/{id}/essay/get-info/{essayId}`
- **请求方法**：`GET`
- **接口说明**：用户查看作文内容，以 PDF 文件形式返回。
- **请求说明**：

  - 路径参数：
    - `id`：学生的唯一标识符
    - `essayId`: 作文的唯一标识符
  - 请求体：无

- 响应说明：

  - 成功响应 (`200 OK`)

    - 响应类型为 `application/pdf`，返回 PDF 文件内容，附带 `Content-Disposition` 头以 inline 方式显示。

  - **失败响应** (`404 Not Found`):

    ```json
      响应体为空
    ```
    




### View Essays `finished`

- **接口路径**：`/api/student/{id}/view-essays`

- **请求方法**：`GET`

- **接口说明**：用户查看作文列表，返回作文除内容外的信息。

- **请求说明**：
  - 路径参数：
    - `id`：学生的唯一标识符

  - 请求体：无

- 响应说明：

  - 响应格式：`JSON`

  - **成功响应** (`200 OK`):

    ```json
    {
      "message": "作文列表获取成功",
      "data": [
        {
          "id": 12345,
          "title": "string",
        },
        {
          "id": 12345,
          "title": "string",
        },
        ...
      ]
    }
    ```

  - **失败响应** (`400 Bad Request`):

    ```json
    {
      "message": "作文列表获取失败",
      "data": null
    }
    ```



### Get Unfinished Practice List `finished`

- **接口路径**：`/api/student/{id}/get-unfinished-practice-list`
- **请求方法**：`GET`
- **接口说明**：学生用户获取未完成练习列表。
- **请求说明**
- 请求头: `Content-Type` : `application/json`
- 请求参数:- 路径参数（Path Variable）：`id` - 学生的唯一标识符
- 请求体(`JSON` 格式)：空
  - **响应说明**
    - 响应格式: `JSON`
      - **成功响应** (`200 OK`):
        ```json
        {
          "message" : "获取成功",
          "data" : [
              {
                "practiceId" : "long",
                "practiceName" : "string"
              },
              {
                "practiceId" : "long",
                "practiceName" : "string"
              },
          ]
        }
        ```
      - **失败响应** (`400 Bad Request`):
        ```json
        {
          "message": "获取失败",
          "data" : null
        }
        ```


### Get Finished Practice List `finished`

- **接口路径**：`/api/student/{id}/get-finished-practice-list`
- **请求方法**：`GET`
- **接口说明**：学生用户获取已完成练习列表。
- **请求说明**
- 请求头: `Content-Type` : `application/json`
- 请求参数:- 路径参数（Path Variable）：`id` - 学生的唯一标识符
- 请求体(`JSON` 格式)：空
  - **响应说明**
    - 响应格式: `JSON`
      - **成功响应** (`200 OK`):
        ```json
        {
          "message" : "获取成功",
          "data" : [
              {
                "practiceId" : "long",
                "practiceName" : "string",
                "practiceTime" : "string",
                "totalScore" : "double"
              },
              {
                "practiceId" : "long",
                "practiceName" : "string",
                "practiceTime" : "string",
                "totalScore" : "double"
              },
          ]
        }
        ```
      - **失败响应** (`400 Bad Request`):
        ```json
        {
          "message": "获取失败",
          "data" : null
        }
        ```




### Get Knowledge Points `finished`

- **接口路径**：`/api/student/{id}/practice/get-knowledge-points`

- **请求方法**：`GET`

- **接口说明**：学生用户自定义生成试卷时，获取可选知识点。

- **请求说明**：
  - 路径参数：
    - `id`：学生的唯一标识符

  - 请求体：无

- 响应说明：

  - 响应格式：`JSON`

  - **成功响应** (`200 OK`):

    ```json
    {
      "message": "知识点获取成功",
      "data": [
        {
          "id": "long",
          "name": "string",
          "description": "string",
          "type": "string"
        },
        {
          "id": "long",
          "name": "string",
          "description": "string",
          "type": "string"
        },
        ...
      ]
    }
    ```

  - **失败响应** (`400 Bad Request`):

    ```json
    {
      "message": "知识点获取失败",
      "data": null
    }
    ```
    


### Generate Practice Self-Define `finished` 

- **接口路径**：`/api/student/{id}/practice/generate-define`
- **请求方法**：`POST`
- **接口说明**：学生用户自定义生成新的练习题。
- **请求说明**
- 请求头: `Content-Type` : `application/json`
  - 请求参数:
    - 路径参数（Path Variable）：`id` - 学生的唯一标识符
- 请求体(`JSON` 格式)：
  ```json
  {
    "name" : "string",
    "knowledgePoints": [
      {
        "knowledgePointId": "long",
        "num": int
      },
      {
        "knowledgePointId": "long",
        "num": int
      },
      ...
    ],
    "questionBodyTypes": [
      {
        "type": "string",
        "num": int
      },
      {
        "type": "string",
        "num": int
      },
      ...
    ]
  }
  ```
- **响应说明**
  - 响应格式: `JSON`
  - **成功响应** (`200 OK`):
  ```json
    {
      "message": "题目已生成",
      "practiceId": "long",
      "data": [
        {
          "practiceQuestionId" : "long",
          "questionBody": "string",//该题是组合题的第一题时才有，否则为空
          "questionContent": "string",
          "type": "string",
          "questionOptions": ["string","string","string","string"],//若不是选择题则为空
          "sequence": "string"
        },
        {
          "practiceQuestionId" : "long",
          "questionBody": "string",//该题是组合题的第一题时才有，否则为空
          "questionContent": "string",
          "type": "string",
          "questionOptions": ["string","string","string","string"],//若不是选择题则为空
          "sequence": "string"
        },
        ...
      ]
    }
  ```
  - **失败响应** (`400 Bad Request`):
  ```json
  {
    "message": "题目发送失败",
    "practiceId": null,
    "data": null
  }
  ```


### Generate Practice Auto `finished`

- **接口路径**：`/api/student/{id}/practice/generate-auto`
- **请求方法**：`POST`
- **接口说明**：学生用户自动生成新的练习题。
- **请求说明**
- 请求头: `Content-Type` : `application/json`
- 请求参数:
  - 路径参数（Path Variable）: `id` - 学生的唯一标识符
  - 查询参数（Query Parameter）: `practiceName` - 练习名称
- 请求体(`JSON` 格式)：无
- **响应说明**
  - 响应格式: `JSON`
  - **成功响应** (`200 OK`):
  ```json
    {
      "message": "题目已生成",
      "practiceId": "long",
      "data": [
        {
          "practiceQuestionId" : "long",
          "questionBody": "string",//该题是组合题的第一题时才有，否则为空
          "questionContent": "string",
          "type": "string",
          "questionOptions": ["string","string","string","string"],//若不是选择题则为空
          "sequence": "string"
        },
        {
          "practiceQuestionId" : "long",
          "questionBody": "string",//该题是组合题的第一题时才有，否则为空
          "questionContent": "string",
          "type": "string",
          "questionOptions": ["string","string","string","string"],//若不是选择题则为空
          "sequence": "string"
        },
        ...
      ]
    }
  ```
  - **失败响应** (`400 Bad Request`):
  ```json
  {
    "message": "题目发送失败",
    "practiceId" : null,
    "data": null
  }
  ```


### Complete Practice `finished`

- **接口路径**：`/api/student/{id}/practice/complete`
- **请求方法**：`POST`
- **接口说明**：学生用户完成练习题，提交答案。
- **请求说明**
- 请求头: `Content-Type` : `application/json`
- 请求参数:- 路径参数（Path Variable）：`id` - 学生的唯一标识符
- 请求体(`JSON` 格式)：
  ```json
  {
    "data" : [
    {
      "practiceQuestionId" : "long",
      "answerContent" : "string"
    },
    {
      "practiceQuestionId" : "long",
      "answerContent" : "string"
    },
  ]
  }
  ```
- **响应说明**
  - 响应格式: `JSON`
  - **成功响应** (`200 OK`):
    ```json
    {
      "message": "练习提交成功",
      "score" : "double"
    }
    ```
  - **失败响应** (`400 Bad Request`):
    ```json
    {
      "message": "练习提交失败",
      "score": null
    }
    ```


### Get Answer `finished`

- **接口路径**：`/api/student/{id}/practice/get-answer`
- **请求方法**：GET
- **接口说明**：学生查看完成的试卷参考答案和提交答案。
- **请求说明**：
- 请求参数：
  - 路径参数（Path Variable）：`id` - 学生的唯一标识符
  - 查询参数（Query Parameter）：`practiceId` - 练习试卷的唯一标识符
  - 请求体：无
- **响应说明**
  - 响应格式: `JSON`
  - **成功响应** (`200 OK`):
    ```json
    {
      "message" : "success",
      "data" : 
      [
        {
          "questionBody": "string",//该题是组合题的第一题时才有，否则为空
          "questionContent" : "string",
          "questionType" : "string",
          "questionOptions": ["string","string","string","string"],//若不是选择题则为空
          "answer" : "string",
          "analysis" : "string",
          "studentAnswer" : "string",
          "score" : "double"
        },
        {
          "questionBody": "string",//该题是组合题的第一题时才有，否则为空
          "questionContent" : "string",
          "questionType" : "string",
          "questionOptions": ["string","string","string","string"],//若不是选择题则为空
          "answer" : "string",
          "analysis" : "string",
          "studentAnswer" : "string",
          "score" : "double"
        }
      ]
    }
    ```
  - **失败响应** (`400 Bad Request`):
    ```json
    {
      "message" : "答案获取失败",
      "data" : null
    }
    ```

### Save Answer `finished`

- **接口路径**：`/api/student/{id}/practice/save`
- **请求方法**：`POST`
- **接口说明**：学生用户暂存已回答的练习题。
- **请求说明**
- 请求头: `Content-Type` : `application/json`
- 请求参数:- 路径参数（Path Variable）：`id` - 学生的唯一标识符
- 请求体(`JSON` 格式)：
  ```json
  {
    "data" : [
      {
        "practiceQuestionId" : "long",
        "answerContent" : "string"
      },
      {
        "practiceQuestionId" : "long",
        "answerContent" : "string"
      }
    ]
  }
  ```
- **响应说明**
  - 响应格式: `JSON`
  - **成功响应** (`200 OK`):
    ```json
    {
      "message": "暂存成功"
    }
    ```
  - **失败响应** (`400 Bad Request`):
    ```json
    {
      "message": "暂存失败"
    }
    ```



### Continue Practice `finished`

- **接口路径**：`/api/student/{id}/continue-practice`
- **请求方法**：`POST`
- **接口说明**：学生用户继续练习的答题。
- **请求说明**
- 请求头: `Content-Type` : `application/json`
- 请求参数:- 路径参数（Path Variable）：`id` - 学生的唯一标识符
- 请求体(`JSON` 格式)：

```json
{
  "practiceId" : "long"
}
```
- **响应说明**
  - 响应格式: `JSON`
    - **成功响应** (`200 OK`):
      ```json
      {
        "message" : "获取成功",
        "data" : [
            {
              "practiceQuestionId" : "long",
              "sequence" : "string",
              "questionBody": "string",//该题是组合题的第一题时才有，否则为空
              "questionContent" : "string",
              "questionType" : "string",
              "questionOptions": ["string","string","string","string"],//若不是选择题则为空
              "answerContent" : "string"
            },
            {
              "practiceQuestionId" : "long",
              "sequence" : "string",
              "questionBody": "string",//该题是组合题的第一题时才有，否则为空
              "questionContent" : "string",
              "questionType" : "string",
              "questionOptions": ["string","string","string","string"],//若不是选择题则为空
              "answerContent" : "string"
            },
        ]
      }
      ```
    - **失败响应** (`400 Bad Request`):
      ```json
      {
        "message": "获取失败",
        "data" : null
      }
      ```


### Delete Practice `finished`
- **接口路径**：`/api/student/{id}/delete-practice`
- **请求方法**：`DELETE`
- **接口说明**：学生用户删除未完成的练习。
- **请求说明**
- 请求头: `Content-Type` : `application/json`
- 请求参数:- 路径参数（Path Variable）：`id` - 学生的唯一标识符
- 请求体(`JSON` 格式)：

```json
{
  "practiceId" : "long"
}
```
- **响应说明**
  - 响应格式: `JSON`
    - **成功响应** (`200 OK`):
      ```json
      {
        "message" : "练习删除成功",
      }
      ```
    - **失败响应** (`400 Bad Request`):
      ```json
      {
        "message": "练习删除失败",
      }
      ```

### Get Unfinished Assignment List `finished`
- **接口路径**：`/api/student/{id}/get-unfinished-assignment-list`
- **请求方法**：`GET`
- **接口说明**：学生用户获取作业列表。
- **请求说明**
- 请求头: `Content-Type` : `application/json`
- 请求参数:- 路径参数（Path Variable）：`id` - 学生的唯一标识符
- 请求体(`JSON` 格式)：空
  - **响应说明**
    - 响应格式: `JSON`
      - **成功响应** (`200 OK`):
        ```json
          {
          "message" : "获取成功",
          "data" : [
              {
                "assignmentId" : "long",
                "title" : "string",
                "description" : "string",
                "startTime" : "string",
                "endTime" : "string"
              },
              {
                "assignmentId" : "long",
                "title" : "string",
                "description" : "string",
                "startTime" : "string",
                "endTime" : "string"
              },
          ]
        }
        ```
      - **失败响应** (`400 Bad Request`):
        ```json
        {
          "message": "获取失败",
          "data" : null
        }
        ```


### Get Finished Assignment List `finished`
- **接口路径**：`/api/student/{id}/get-finished-assignment-list`
- **请求方法**：`GET`
- **接口说明**：学生用户获取作业列表。
- **请求说明**
- 请求头: `Content-Type` : `application/json`
- 请求参数:- 路径参数（Path Variable）：`id` - 学生的唯一标识符
- 请求体(`JSON` 格式)：空
  - **响应说明**
    - 响应格式: `JSON`
      - **成功响应** (`200 OK`):
        ```json
        {
          "message" : "获取成功",
          "data" : [
              {
                "assignmentId" : "long",
                "title" : "string",
                "description" : "string",
                "startTime" : "string",
                "endTime" : "string",
                "totalScore" : "double"
              },
              {
                "assignmentId" : "long",
                "title" : "string",
                "description" : "string",
                "startTime" : "string",
                "endTime" : "string",
                "totalScore" : "double"
              },
          ]
        }
        ```
      - **失败响应** (`400 Bad Request`):
        ```json
        {
          "message": "获取失败",
          "data" : null
        }
        ```




### Get Average Homework Score and Class Rank `finished`

- **接口路径**：`/api/student/{id}/get-avg-score`
- **请求方法**：GET
- **接口说明**：学生获取自己的作业平均分和班级排名。
- **请求说明**：
  - 请求参数：
    - 路径参数（Path Variable）：`id` - 学生的唯一标识符
  - 请求体：无

- **响应说明**：
  - 响应格式：`JSON`
  - **成功响应** (`200 OK`):
    ```json
    {
      "message": "学生作业平均分和班级排名获取成功",
      "data": {
          "averageHomeworkScore": "double", // 作业平均分,例84
          "classRank": "long"// 班级排名，例6
      }
    }
- **失败响应** (`400 Bad Request`):
  ```json
  {
    "message": "获取作业平均分和班级排名失败",
    "data": null
  }
  ```

### Get Multidimensional Scores `finished`
- **接口路径**：`/api/student/{id}/get-multidimensional-scores`
- **请求方法**：GET
- **接口说明**：学生获取自己的各类题目得分率。
- **请求说明**：
  - 请求参数：
    - 路径参数（Path Variable）：`id` - 学生的唯一标识符
  - 请求体：无
- **响应说明**：
  - 响应格式：`JSON`
  - **成功响应** (`200 OK`):
    ```json
    {
      "message": "各类题目得分率获取成功",
      "data": [
        {
          "name": "string",
          "score": "double"
        },
        {
          "name": "string",
          "score": "double"
        },
        ...
      ]
    }
    ```
  - **失败响应** (`400 Bad Request`):
    ```json
    {
      "message": "得分率获取失败",
      "data": null
    }
    ```

### Get Weakness Scores `finished`

- **接口路径**：`/api/student/{id}/get-weakness-scores`
- **请求方法**：GET
- **接口说明**：学生获取自己的短板得分，包括短板名称和得分率。
- **请求说明**：
  - 请求参数：
    - 路径参数（Path Variable）：`id` - 学生的唯一标识符
  - 请求体：无
- **响应说明**：
  - 响应格式：`JSON`
  - **成功响应** (`200 OK`):
    ```json
    {
      "message": "学生短板得分率获取成功",
      "data": [
        {
          "type": "string", //题型（知识点大类）
          "weaknessName": "string", // 短板名称（知识点小类）
          "weaknessScore": "double" // 短板得分率
        },
        {
          "type": "string",
          "weaknessName": "string",
          "weaknessScore": "double"
        },
        ...
      ]
    }
    ```
  - **失败响应** (`400 Bad Request`):
    ```json
    {
      "message": "获取学生短板得分率失败",
      "data": null
    }
    ```

### Get Score Fluctuations `finished`

- **接口路径**：`/api/student/{id}/score-fluctuations`
- **请求方法**：GET
- **接口说明**：学生获取自己的历史分数波动数据。
- **请求说明**：
  - 请求参数：
    - 路径参数（Path Variable）：`id` - 学生的唯一标识符
  - 请求体：无

- **响应说明**：
  - 响应格式：`JSON`
  - **成功响应** (`200 OK`):
    ```json
    {
      "message": "学生历史分数波动数据获取成功",
      "data": [
        {
          "date": "string", //提交时间
          "score": "double"
        },
        {
          "date": "string",
          "score": "double"
        },
        ...
      ]
    }
    ```
  - **失败响应** (`400 Bad Request`):
    ```json
    {
      "message": "获取历史分数波动数据失败",
      "data": null
    }
    ```
    


### Get Homework Detail `finished`

- **接口路径**：`/api/student/{id}/homework/get-detail`
- **请求方法**：GET
- **接口说明**：学生获取作业详情。
- **请求说明**:
  - **请求参数**: 
    - 路径参数（Path Variable）：`id` - 学生的唯一标识符
    - 查询参数（Query Parameter）： `assignmentId` - 作业的唯一标识符
  - **请求体**：无
- **响应说明**：
  - 响应格式：`JSON`
  - **成功响应** (`200 OK`):
    ```json
      {
        "message" : "获取成功",
        "data" : [
            {
              "submissionAnswerId" : "long",
              "sequence" : "string",
              "body": "string",
              "questionContent" : "string",
              "questionType" : "string",
              "questionOptions": ["string","string","string","string"],//若不是选择题则为空
              "answerContent" : "string"
            },
            {
              "submissionAnswerId" : "long",
              "sequence" : "string",
              "body": "string",
              "questionContent" : "string",
              "questionType" : "string",
              "questionOptions": ["string","string","string","string"],//若不是选择题则为空
              "answerContent" : "string"
            },
            ...
        ]
      }
    ```
    - **失败响应** (`400 Bad Request`):
      ```json
      {
        "message": "获取失败",
        "data" : null
      }
      ```



### Complete Homework `finished`

- **接口路径**：`/api/student/{id}/homework/complete`
- **请求方法**：`POST`
- **接口说明**：学生用户完成作业，提交答案。
- **请求说明**
- 请求头: `Content-Type` : `application/json`
- 请求参数:- 路径参数（Path Variable）：`id` - 学生的唯一标识符
- 请求体(`JSON` 格式)：
  ```json
  {
    "data" : [
    {
      "submissionAnswerId" : "long",
      "answerContent" : "string"
    },
    {
      "submissionAnswerId" : "long",
      "answerContent" : "string"
    },
    ...
  ]
  }
  ```
- **响应说明**
  - 响应格式: `JSON`
  - **成功响应** (`200 OK`):
    ```json
    {
      "message": "提交成功"
    }
    ```
  - **失败响应** (`400 Bad Request`):
    ```json
    {
      "message": "提交失败"
    }
    ```



### Get Homework Answer `finished`

- **接口路径**：`/api/student/{id}/homework/get-answer`
- **请求方法**：GET
- **接口说明**：学生查看完成的作业参考答案和提交的答案。
- **请求说明**：
- 请求参数：
  - 路径参数（Path Variable）：`id` - 学生的唯一标识符
  - 查询参数（Query Parameter）：`assignmentId` - 作业的唯一标识符
  - 请求体：无
- **响应说明**
  - 响应格式: `JSON`
  - **成功响应** (`200 OK`):
    ```json
    {
      "message" : "success",
      "data" : 
      [
        {
          "sequence": "string",
          "body": "string",
          "questionContent" : "string",
          "questionType" : "string",
          "questionOptions": ["string","string","string","string"],//若不是选择题则为空
          "answer" : "string",
          "analysis" : "string",
          "studentAnswer" : "string",
          "score" : "double",
          "feedback" : "string"
        },
        {
          "sequence": "string",
          "body": "string",
          "questionContent" : "string",
          "questionType" : "string",
          "questionOptions": ["string","string","string","string"],//若不是选择题则为空
          "answer" : "string",
          "analysis" : "string",
          "studentAnswer" : "string",
          "score" : "double",
          "feedback" : "string"
        },
        ...
      ]
    }
    ```
  - **失败响应** (`400 Bad Request`):
    ```json
    {
      "message" : "答案获取失败",
      "data" : null
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
      "role" : number,//0 or 1 // 0 是正常老师
      "id" : 12345,
      "token" : token
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
          "username": "string",
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

### Update Username `finished`

- **接口路径**：`/api/teacher/{id}/update-username`
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
  - **失败响应** (`400 Bad Request`):
    
    ```json
    {
      "message": "用户名修改失败，用户名已存在或其他错误",
    }
    ```

### Update Phone Number `finished`

- **接口路径**：`/api/teacher/{id}/update-phoneNumber`
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
  - **失败响应** (`400 Bad Request`):
    
    ```json
    {
      "message": "手机号修改失败，手机号已存在或格式错误",
    }
    ```

### Update Name`finished`

- **接口路径**：`/api/teacher/{id}/update-name`

- **请求方法**：PUT

- **接口说明**：教师用户修改自己的姓名。

- **请求说明**：
  
  - 请求参数：
    - 路径参数（Path Variable）：`teacherId` - 教师的唯一标识符
    - 请求体(`JSON` 格式)：
    ```json
    {
      "name": "name"
    }
    ```
    - `phoneNumber`：教师希望修改的新手机号。
  
- **响应说明**：
  - 响应格式：`JSON`
  - **成功响应** (`200 OK`):
    
    ```json
    {
      "message": "姓名修改成功",
    }
    ```
  - **失败响应** (`400 Bad Request`):
    
    ```json
    {
      "message": "姓名修改失败，...",
    }
    ```

### View Curriculum Standard  `finished`

- **接口路径**：`/api/teacher/{id}/view-curriculum-standard`

- **请求方法**：`GET`

- **接口说明**：教师用户查看课程标准（课标），以 PDF 文件形式返回。

- **请求说明**：

  - 路径参数：
    - `id`：教师的唯一标识符。
  - 请求体：无

- 响应说明：

  - 成功响应 (`200 OK`)
    - 响应类型为 `application/pdf`，返回 PDF 文件内容，附带 `Content-Disposition` 头以 inline 方式显示。

  - **失败响应** (`404 Not Found`):

    ```json
    响应体为空
    ```

### View Knowledge Point `finished`

- **接口路径**：`/api/teacher/{id}/view-knowledge-point`

- **请求方法**：`GET`

- **接口说明**：教师用户查看知识点。

- **请求说明**：

  - 路径参数：
    - `id`：教师的唯一标识符。
  - 请求体：无

- 响应说明：

  - 相应格式: `JSON`
  
  - 成功响应 (`200 OK`)
  
    ```json
    {
        "message": "获取成功",
        "knowledgePoints": {
            "Math": [
                {
                    "name": "Algebra",
                    "type": "Math",
                    "description": "Basic Algebra"
                },
                {
                    "name": "Geometry",
                    "type": "Math",
                    "description": "Basic Geometry"
                }
            ],
            "Science": [
                {
                    "name": "Physics",
                    "type": "Science",
                    "description": "Basic Physics"
                }
            ]
        }
    }
    
    ```
  
    
  
  - **失败响应** (`400 Bad Request`):
  
    ```json
    {
        "message" : "获取失败,  ...",
        "knowledgePoints": null
    }
    ```
  

### Create Class `finished`

- **接口路径**：`/api/teacher/{id}/create-class`
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
      "classCode": "string",
      "classId": 12345
    }
    ```
  - **失败响应** (`400 Bad Request`):
  
    ```json
    {
      "message": "班级创建失败",
      "classCode" : null,
      "classId" : null
    }
    ```

### Update Class `finished`

- **接口路径**：`/api/teacher/{id}/update-class`

- **请求方法**：PUT

- **接口说明**：教师用户更改班级信息，传输班级名称和班级描述。

- **请求说明**：
  - 请求参数：
    - 路径参数（Path Variable）：`id` - 老师的唯一标识符
  
  - 请求体(`JSON` 格式)：

    ```json
    {
      "classId" : 12345,
      "className" : "string", // 班级名称
      "classDescription" : "string" // 班级描述
    }
    ```
  
- **响应说明**：
  - 响应格式：`JSON`
  - **成功响应** (`200 OK`):
    ```json
    {
      "message" : "更新成功"
    }
    ```
  - **失败响应** (`400 Bad Request`):
  
    ```json
    {
      "message": "更新失败, ...",
    }
    ```

### Create Group `finished`

- **接口路径**：`/api/teacher/{id}/create-group` 

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
         "studentIds": [number, number, number], // 学生ID的列表
         "groupDescription" : "string" // 小组描述
      
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

- **接口路径**：`/api/teacher/{id}/get-classes`
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

- **接口路径**：`/api/teacher/{id}/get-groups`

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

- **接口路径**：`/api/teacher/{id}/get-class-members`
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

### Get KnowledgePoint List `finished`

- **接口路径**：`/api/teacher/{id}/list-knowledge-point`
- **请求方法**：GET
- **接口说明**：获取教师的知识点分类和知识点名称。
- **请求说明**：
  
  - 请求参数：
    - 路径参数（Path Variable）：`id` - 教师的唯一标识符
  - 请求体：无
  
- **响应说明**：
  - 响应格式：`JSON`
  - **成功响应** (`200 OK`):
    ```json
    {
        "message": "获取成功",
        "knowledgePoints": {
            "Math": [
                {
                    "id" : 12345,
                    "name": "Algebra",
                },
                {
                    "id" : 12345
                    "name": "Geometry",
                }
            ],
            "Science": [
                {
                    "id" : 12345
                    "name": "Physics",
                }
            ]
        }
    }
    
    ```
  - **失败响应** (`400 Bad Request`):
    
    ```json
    {
      "message": "获取知识点信息失败",
      "content": null
    }
    ```

### Upload Question `finished`

- **接口路径**：`/api/teacher/{id}/upload-question`
- **请求方法**：POST
- **接口说明**：上传题目信息，包含题目类型、题目内容、选项（若有）等信息。
- **请求说明**：
  - 请求参数：路径参数 ：`id`  老师唯一标识。
  - 请求体(`JSON` 格式)：
    ```json
    {
      "questionType": "string", // '单题为null', '文言文阅读', '记叙文阅读', '非连续性文本阅读', '古诗词曲鉴赏', '名著阅读'
      "body": "string", // 题目内容
      "questions": [
        {
          "type": "CHOICE", // 题目类型，CHOICE, FILL_IN_BLANK, SHORT_ANSWER, ESSAY
          "problem": "string", // 问题描述
          "choices": ["string", "string", "string"], // 若题目为选择题，提供选项，若不是选择题则为空数组
          "answer": ["string", "string", "string"], // 题目答案
          "analysis":"string", // 题目解析
          "knowledgePointId": 12345 // 知识点ID
        }
      ]
    }
    ```
- **响应说明**：
  - 响应格式：`JSON`
  - **成功响应** (`200 OK`):
    ```json
    {
      "message": "上传成功"
    }
    ```
  - **失败响应** (`400 Bad Request`):
    ```json
    {
      "message": "请求参数错误"
    }
    ```

### Get Student's Average Homework Score and Class Rank `finished`

- **接口路径**：`/api/teacher/{id}/get-student-situation`
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
          "averageHomeworkScore": "double", // 作业平均分,例84
          "classRank": "long"// 班级排名，例6
      }
    }
- **失败响应** (`400 Bad Request`):
  ```json
  {
    "message": "获取作业平均分和班级排名失败",
    "data": null
  }
  ```

### Get Student's Multidimensional Scores `finished`

- **接口路径**：`/api/teacher/{id}/get-student-multidimensional-scores`
- **请求方法**：GET
- **接口说明**：教师用户获取某个学生的多维数据得分率。
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
      "message": "各类题目得分率获取成功",
      "data": [
        {
          "name": "string",
          "score": "double"
        },
        {
          "name": "string",
          "score": "double"
        },
        ...
      ]
    }
    ```
  - **失败响应** (`400 Bad Request`):
    ```json
    {
      "message": "得分率获取失败",
      "data": null
    }
    ```


### Get Student's Weakness Scores `finished`

- **接口路径**：`/api/teacher/{id}/get-student-weakness-scores`
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
      "message": "学生短板得分率获取成功",
      "data": [
        {
          "type": "string", //题型（知识点大类）
          "weaknessName": "string", // 短板名称（知识点小类）
          "weaknessScore": "double" // 短板得分率
        },
        {
          "type": "string",
          "weaknessName": "string",
          "weaknessScore": "double"
        },
        ...
      ]
    }
    ```
  - **失败响应** (`400 Bad Request`):
    ```json
    {
      "message": "获取学生短板得分率失败",
      "data": null
    }
    ```


### Get Student's Historical Homework Scores `finished`

- **接口路径**：`/api/teacher/{id}/get-student-historical-homework-scores`
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
      "message": "学生历史分数波动数据获取成功",
      "data": [
        {
          "date": "string", //提交时间
          "score": "double"
        },
        {
          "date": "string",
          "score": "double"
        },
        ...
      ]
    }
    ```
  - **失败响应** (`400 Bad Request`):
    ```json
    {
      "message": "获取历史分数波动数据失败",
      "data": null
    }
    ```

### Get Class's Historical Average Score `finished`

- **接口路径**：`/api/teacher/class/historical-scores`
- **请求方法**：GET
- **接口说明**：教师用户获取某个班级的历史平均分列表。
- **请求说明**：
  - 请求参数：
    - 查询参数（Path Variable）：`classId` - 班级的唯一标识符
  - 请求体：无

- **响应说明**：
  - 响应格式：`JSON`
  - **成功响应** (`200 OK`):
    ```json
    {
      "message": "班级历史平均分获取成功",
      "avgScore": "double",
      "data": [
        {
          "score": 85,
          "endTime": "2024-10-20T23:59:59"
        },
        {
          "score": 90,
          "endTime": "2024-09-15T23:59:59"
        }
      ]
    }
    ```
  - **失败响应** (`400 Bad Request`):
    ```json
    {
      "message": "获取班级历史平均分失败",
      "avgScore": null,
      "data": null
    }
    ```

### Disband Class `finished`

- **接口路径**：`/api/teacher/{id}/classes/disband`
- **请求方法**：DELETE
- **接口说明**：教师用户解散某个班级。
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

- **接口路径**：`/api/teacher/{teacherId}/classes/remove-student`
- **请求方法**：DELETE
- **接口说明**：教师用户将某个学生移出指定班级。
- **请求说明**：
  - 请求参数：
    - 路径参数（Path Variable）：`teacherId` - 教师的唯一标识符
    - 查询参数（Path Variable）：`studentId` - 学生的唯一标识符
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

- **接口路径**：`/api/teacher/{teacherId}/groups-members`

- **请求方法**：GET

- **接口说明**：教师用户查看某个小组的成员构成。

- **请求说明**：
  
  - 请求参数：
    - 路径参数（Path Variable）：`teacherId` - 教师的唯一标识符
    - 查询参数（Path Variable）：`groupId` - 小组的唯一标识符
  - 请求体：无

- **响应说明**：
  - 响应格式：`JSON`
  - **成功响应** (`200 OK`):
    ```json
    {
      "message": "小组成员构成获取成功",
      "students": [
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

- **接口路径**：`/api/teacher/group/historical-scores`
- **请求方法**：GET
- **接口说明**：教师用户查看某个小组的历史得分率，并包括当次作业的截止时间。
- **请求说明**：
  - 请求参数：
    - 查询参数（Path Variable）：`groupId` - 小组的唯一标识符
  - 请求体：无

- **响应说明**：
  - 响应格式：`JSON`
  - **成功响应** (`200 OK`):
    ```json
    {
      "message": "小组历史得分率获取成功",
      "avgScore": "double",
      "data": [
        {
          "score": 85,
          "endTime": "2024-10-20T23:59:59"
        },
        {
          "score": 90,
          "endTime": "2024-09-15T23:59:59"
        }
      ]
    }
    ```
  - **失败响应** (`400 Bad Request`):
    ```json
    {
      "message": "获取小组历史得分率失败",
      "avgScore": null,
      "data": null
    }
    ```

### Disband Group `finished`

- **接口路径**：`/api/teacher/{teacherId}/disband-group`

- **请求方法**：DELETE

- **接口说明**：教师用户解散某个小组。

- **请求说明**：
  
  - 请求参数：
    - 路径参数（Path Variable）：`teacherId` - 教师的唯一标识符
    
      查询参数（Path Variable）：`groupId` - 小组的唯一标识符
    
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

- **接口路径**：`/api/teacher/{id}/group/remove-student`
- **请求方法**：DELETE
- **接口说明**：教师用户将某个学生移出指定小组。
- **请求说明**：
  - 请求参数：
    - 路径参数（Path Variable）：`id` - 教师的唯一标识符
    - 查询参数（Path Variable）：`groupId` - 小组的唯一标识符
    - 查询参数（Path Variable）：`studentId` - 学生的唯一标识符
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


### Change Password `finished`

- **接口路径**：`/api/teacher/{id}/change-password`
- **请求方法**：POST
- **接口说明**：教师账号修改密码。
- **请求说明**
  - 请求头: `Content-Type` : `application/json`
  - 请求参数:
    - 路径参数（Path Variable）：`id` - 教师的唯一标识符
    - 请求体(`JSON` 格式)：
    ```json
    {
      "password" : "string", // 旧密码
      "newPassword" : "string"
    }
    ```
- **响应说明**
  - 响应格式: `JSON`
  - **成功响应** (`200 OK`):
    ```json
    {
      "message" : "success"
    }
    ```
  - **失败响应** (`400 Bad Request`):
    ```json
    {
      "message" : "修改密码失败"
    }
    ```



### Get Applications `finished`

- **接口路径**：`/api/teacher/{id}/get-applications`
- **请求方法**：GET
- **接口说明**：教师获取加入班级申请。
- **请求说明**
  - 请求头: `Content-Type` : `application/json`
  - 请求参数:
    - 路径参数（Path Variable）：`id` - 教师的唯一标识符
    - 查询参数（Query Parameter）`classId` - 班级唯一标识符
    - 请求体：无
- **响应说明**
  - 响应格式: `JSON`
  - **成功响应** (`200 OK`):
    ```json
    {
      "message" : "success",
      "data" : [
        {
           "joinClassId": "long",
           "studentId": "long",
           "userName": "string",
           "name": "string"
        },
        {
           "joinClassId": "long",
           "studentId": "long",
           "userName": "string",
           "name": "string"
        }
    ]
    }
    ```
  - **失败响应** (`400 Bad Request`):
    ```json
    {
      "message" : "读取失败",
      "data": null
    }
    ```



### Allow Application `finished`

- **接口路径**：`/api/teacher/{id}/allow-application`
- **请求方法**：GET
- **接口说明**：教师允许加入班级申请。
- **请求说明**
  - 请求头: `Content-Type` : `application/json`
  - 请求参数:
    - 路径参数（Path Variable）：`id` - 教师的唯一标识符
    - 查询参数（Query Parameter）`joinClassId` - 学生加入班级行为的唯一标识符
    - 请求体：无
- **响应说明**
  - 响应格式: `JSON`
  - **成功响应** (`200 OK`):
    ```json
    {
      "message" : "success",
    }
    ```
  - **失败响应** (`400 Bad Request`):
    ```json
    {
      "message" : "错误"
    }
    ```

### 获取问题（根据筛选条件） `finished`

- **接口路径**：`/api/teacher/search-questions`
- **请求方法**：POST
- **接口说明**：教师查找题目。

- **请求说明**
    - 请求头: `Content-Type` : `application/json`
    - 请求参数:
      - 请求体(`JSON` 格式)：
      ```json
      {
      "type": "选择",        // 题目类型，选择、填空、问答、作文、全部
      "knowledgeType": "积累与运用", // 知识点大类
      "knowledgeId": "1", // 知识点ID
      "difficulty": "普通",  // 难度级别，容易、普通、困难、全部
      "mode": "latest",      // 模式，如最新上传latest、最多使用mostUsed、默认‘’等
      "sortOrder": "asc",    // 排序顺序，"asc" 或 "desc"
      "page": 1,             // 当前页
      "pageSize": 10,        // 每页显示数量
      "search": "关键词"     // 搜索关键词（可能为空）
      }
      ```

- **响应说明**：
  - **响应格式**：JSON
  - **成功响应**（200 OK）：
  ```json
  {
  "bigQuestions": [ //若传的是knowledgeId则为空
    {
      "bodyId": 1,
      "body": "题干1",          // 题干 
      "subQuestion": [
        {
        "question": "问题1",      // 问题内容
        "answer": "答案1",        // 答案
        "explanation": "解析1",   // 解析
        "type": "选择",           // 题型
        "knowledge": "知识点1"    // 知识点
        },
      ], 
      "difficulty": "普通",     // 难度
    }
  ],
  "questions": [ //若传的是type则为空
  {
    "questionId": 1, //如果传的是knowledgeId这个不为空
    "body": "题干1",          // 题干 可能为空(单题)
    "question": "问题1",      // 问题内容
    "answer": "答案1",        // 答案
    "explanation": "解析1",   // 解析
    "difficulty": "普通",     // 难度
    "type": "选择",           // 题型
    "knowledge": "知识点1"    // 知识点
  }
  ],
  "totalCount": 50,    // 总记录数
  "pageSize": 10,      // 每页显示的题目数量
  "currentPage": 1,    // 当前页
  "totalPages": 5      // 总页数
  }
  
  ```
  - **失败响应**（400 Bad Request）：
    
    ```json
    {
      "message": "获取失败"
    }
    ```

### generate testPaper `finished`

- **接口路径**：`/api/teacher/generate-paper`

- **请求方法**：POST

- **接口说明**：教师生成试卷题目。

- **请求说明**

  - 请求头: `Content-Type` : `application/json`

  - 请求参数:

    - 请求体(`JSON` 格式)：

      ```json
      {
        "name": "试卷名字",
        "creatorId": 12345, // 老师id
        "totalScore": 100,
        "difficulty" : 0.25,
        "questions":[
          {
              "id" : 12345, //可能是questionId或bodyId，根据type
              "type": "string",//"big" or "small", //题目类型
              "sequence": 1234, //题目序号
              "score" : 5
          },
        ]
      }
      ```

- **响应说明**

  - **响应格式**：`JSON`

  - **成功响应** (`200 OK`):

    ```json
    {
      "message" : "success",
    }
    ```

  - **失败响应** (`400 Bad Request`):

    ```json
    {
      "message" : "错误",
    }
    ```

### get papers `finished`

- **接口路径**：`/api/teacher/papers/{id}`

- **请求方法**：GET

- **接口说明**：教师用户通过ID 获取自己出过的试卷。

- **请求说明**

  - 请求参数：
    - 路径参数（Path Variable）：`id` - 教师的唯一标识符

- **响应说明**：

  - 响应格式：`JSON`

  - **成功响应** (`200 OK`):

    ```json
    {
      "message": "success",
      "papers": [
          {
    		"id": 12345, //试卷id
            "name": "试卷名称",
            "createTime": "生成时间",
            "totalScore": 100,
            "difficulty": 64.4 //难度
          }
      ]
    }
    ```
  
  - **失败响应** (`400 Not Found`):
  
    ```json
    {
      "message": "错误",
    }
    ```

### Get Paper Detail `finished`

- **接口路径**：`/api/teacher/paper`

- **请求方法**：GET

- **接口说明**：教师用户通过试卷ID获取试卷详情。

- **请求说明**：

  - 请求参数：
    - `请求`参数（Path Variable）：`id` - paper 的 Id

- **响应说明**：

  - 响应格式：`JSON`

  - **成功响应** (`200 OK`):

    ```json
    {
      "message": "success",
      "totalScore": 100, // 总分
      "questions": [
          {
           	"body": "题干， 单题为空",
            "sequence": 12345, //题号
        	"score": 4, //分数
            "subQuestions": [ //大题时不为空
                {    
                    "question": "问题内容", 
                    "answer" : "答案", 
                    "explanation": "解析", 
                    "options": ["选项1", "选项2"], 
                    "type" : "选择、填空、简答、作文",
                    "knowledge" : "知识点",
    				"score" : 1//小题分数
                }
            ],
            "question": "问题内容", //小题时不为空
            "answer" : "答案", //小题时不为空
            "explanation": "解析", //小题时不为空
            "options": ["选项1", "选项2"], //小题且为选择时不为空
            "type" : "选择、填空、简答、作文",//小题时不为空
            "knowledge" : "知识点",
          }
      ]
    }
    ```
  
  - **失败响应** (`400 Not Found`):

### Get uploaded questions `finished`

- **接口路径**：`/api/teacher/uploaded-questions`

- **请求方法**：GET

- **接口说明**：教师用户查看自己上传的题目。

- **响应说明**：

  - 响应格式：`JSON`

  - **成功响应** (`200 OK`):

    ```json
    {
      "message": "success",
      "uploadedQuestions": [
        {
          "questionId" : 123,
          "type" : "small" or "big"
          "uploadTime": "string",
          "status": "未审核" or "通过" or "拒绝"
          "comment": "备注"
          "executeTeacher" : "审核老师名字",d
        }
      ]
    }
    ```

###  Get All Waiting Questions `finished`

- **接口路径**：`/api/teacher/get-all-waiting-questions`

- **请求方法**：GET

- **接口说明**：审核教师获取所有未审核题目。

- **请求说明**：

- **响应说明**：

  - **响应格式**：JSON

  - **成功响应**（200 OK）：

    ```json
    {
      "message": "获取题目成功",
      "questions": [
        {
          "id": "long", // 对应数据库中upload_question 的 id 字段
          "questionId" : 123,
          "type" : "string",//"small" or "big"
          "uploadTime": "string",
          "uploadTeacher" : "老师名字"
        },
         ...
      ]
    }
    ```
  
  - **失败响应**（400 Bad Request）：
  
    ```json
    {
      "message": "题目获取失败",
    }
    ```

###  Get All Access Questions `finished`

- **接口路径**：`/api/teacher/get-all-access-questions`

- **请求方法**：GET

- **接口说明**：审核教师获取所有`已审核`题目。

- **请求说明**：

- **响应说明**：

  - **响应格式**：JSON

  - **成功响应**（200 OK）：

    ```json
    {
      "message": "获取题目成功",
      "questions": [
        {
          "id": "long", // 对应数据库中的 id 字段
          "questionId" : 123,
          "type" : "small" or "big"
          "uploadTime": "string",
          "uploadTeacher" : "老师名字"
        },
         ...
      ]
    }
    ```

  - **失败响应**（400 Bad Request）：

    ```json
    {
      "message": "题目获取失败",
    }
    ```

###  Get  Question `finished`

- **接口路径**：`/api/teacher/get-question`

- **请求方法**：GET

- **接口说明**：审核教师获取题目具体信息。

- **请求说明**：

  - 请求参数:
    - 查询参数（Query Parameter）`questionId` - 题目的唯一标识符
    - 查询参数（Query Parameter）`type` - 题目的类型(`small` or `big`)

- **响应说明**：k

  - **响应格式**：JSON

  - **成功响应**（200 OK）：

    ```json
    {
      "message": "操作成功",  // 响应的状态信息，表示操作是否成功
      "body": "数据已成功返回",  // 响应的具体内容描述
    
      "content": "小题内容示例",  // 小题的具体内容
      "answer": "答案示例",  // 小题的答案
      "explanation": "解释示例",  // 小题的解释
      "options": [
        "选项A",  // 选项A
        "选项B",  // 选项B
        "选项C",  // 选项C
        "选项D"   // 选项D
      ],
      "type": "选择题",  // 题目类型，例如选择题、填空题等
      "knowledgePoint": "知识点示例",  // 该题目关联的知识点
    
      "subQuestions": [  // 包含多个子问题的大题部分
        {
          "content": "大题子问题1",  // 子问题1的内容
          "answer": "子问题1的答案",  // 子问题1的答案
          "explanation": "子问题1的解释",  // 子问题1的解释
          "options": [
            "选项A1",  // 子问题1的选项A
            "选项B1",  // 子问题1的选项B
            "选项C1",  // 子问题1的选项C
            "选项D1"   // 子问题1的选项D
          ],
          "type": "选择题",  // 子问题1的题目类型
          "knowledgePoint": "子问题1知识点"  // 子问题1的知识点
        },
        {
          "content": "大题子问题2",  // 子问题2的内容
          "answer": "子问题2的答案",  // 子问题2的答案
          "explanation": "子问题2的解释",  // 子问题2的解释
          "options": [
            "选项A2",  // 子问题2的选项A
            "选项B2",  // 子问题2的选项B
            "选项C2",  // 子问题2的选项C
            "选项D2"   // 子问题2的选项D
          ],
          "type": "选择题",  // 子问题2的题目类型
          "knowledgePoint": "子问题2知识点"  // 子问题2的知识点
        }
      ]
    }
    ```

  - **失败响应**（400 Bad Request）：

    ```json
    {
      "message": "题目获取失败",
      "data": null
    }
    ```

### Deny Upload Question `finished`

- **接口路径**：`/deny-upload-question`

- **请求方法**：`PUT`

- **接口说明**：拒绝上传指定题目。

**请求说明**

- **请求格式**: `JSON`

  ```json
  {
      "id" : 12345,// get all waiting quesitons中的id
      "comment" : "备注"
  }
  ```

  

 **响应说明**

- **响应格式**：`JSON`

- **成功响应（200 OK）**：

    ```json
    {
      "message": "题目上传已被拒绝"
    }
    ```

- **失败响应（400 Bad Request）**：

    ```json
    {
      "message": "无效的题目ID或类型"
    }
    ```


### Delete Question `finished`

- **接口路径**：`/api/teacher/delete-question`

- **请求方法**：DELETE

- **接口说明**：教师删除指定ID的题目。

- **请求说明**：

  - **查询参数**：`questionId`：题目的唯一标识符。
  - 查询参数:  `type`: "big"OR"small"，判断大题还是小题。

- **响应说明**：

  - **响应格式**：JSON

  - **成功响应**（200 OK）：

    ```json
    {
      "message": "删除成功"
    }
    ```

  - **失败响应**（400 Bad Request）：

    ```json
    {
      "message": "删除失败"
    }
    ```


### Approve Question `finished`

- **接口路径**：`/api/teacher/approve-question`

- **请求方法**：PUT

- **接口说明**：审核老师批准题目上传

- **请求说明**：

  - 请求体(`JSON` 格式)：

    ```json
    {
      "id": 123, //upload_question的id
      "body": "string", //type为small的时候为空
      "comment": "备注",
      "questions": [ //每个字段均不能为空
        {
          "problem": "string", // 问题描述
          "choices": ["string", "string", "string"], // 若题目为选择题，提供选项，若不是选择题则为空数组
          "answer": "String", // 题目答案
          "analysis":"string", // 题目解析
        }
      ]
    }
    ```

- **响应说明**：

  - 响应格式：`JSON`

  - **成功响应** (`200 OK`):

    ```json
    {
      "message": "成功"
    }
    ```

  - **失败响应** (`400 Bad Request`):

    ```json
    {
      "message": "请求参数错误"
    }
    ```

### Modify Question `finished`

- **接口路径**：`/api/teacher/modify-question`

- **请求方法**：PUT

- **接口说明**：审核老师批准题目上传

- **请求说明**：

  - 请求体(`JSON` 格式)：

    ```json
    {
      "id": 123, //upload_question的id
      "body": "string", //type为small的时候为空
      "questions": [ //每个字段均不能为空
        {
          "problem": "string", // 问题描述
          "choices": ["string", "string", "string"], // 若题目为选择题，提供选项，若不是选择题则为空数组
          "answer": "String", // 题目答案
          "analysis":"string", // 题目解析
        }
      ]
    }
    ```

- **响应说明**：

  - 响应格式：`JSON`

  - **成功响应** (`200 OK`):

    ```json
    {
      "message": "成功"
    }
    ```

  - **失败响应** (`400 Bad Request`):

    ```json
    {
      "message": "请求参数错误"
    }
    ```


### Get Assignment List `finished`

- **接口路径**：`/api/teacher/{id}/get-assignment-list`
- **请求方法**：`GET`
- **接口说明**：老师获取作业列表。
- **请求说明**：
  - 请求参数：
    - 路径参数： `id`：教师的唯一标识符（`Long` 类型）
  - 请求体： 无
- **响应说明**：
  - 响应格式：`JSON`
  - 成功响应（200 OK）：
    ```json
      {
        "message": "success",
        "data": [
          {
            "assignmentId": "long",
            "assignmentTitle": "string",
            "assignmentDescription": "string",
            "startTime": "string",
            "endTime": "string",
            "paperId": "long"
          },
          ...
        ]
      }
    ```
  - 失败响应（400 Bad Request）：
    ```json
    {
      "message": "获取失败",
      "data": null
    }
    ```


### Get Submission List `finished`

- **接口路径**：`/api/teacher/{id}/get-submission-list`
- **请求方法**：`GET`
- **接口说明**：老师获取某个作业的提交列表。
- **请求说明**：
  - 请求参数：
    - 路径参数： `id`：教师的唯一标识符（`Long` 类型）
    - 查询参数： `assignmentId`：作业的唯一标识符（`Long` 类型）
  - 请求体： 无
- **响应说明**：
  - 响应格式：`JSON`
  - 成功响应（200 OK）：
    ```json
      {
        "message": "success",
        "assignmentId": "long",
        "data": [
          {
            "studentId": "long",
            "studentName": "string",
            "isSubmitted": "number",//1是已提交，0是未提交
            "submitTime": "string",
            "totalScore": "double",
            "isMarked": "number",//1是已批阅，0是未批阅
          },
          ...
        ]
      }
    ```
  - 失败响应（400 Bad Request）：
    ```json
    {
      "message": "获取失败",
      "assignmentId": null,
      "data": null
    }
    ```


### Get Submission `finished`

- **接口路径**：`/api/teacher/{id}/get-submission`
- **请求方法**：`GET`
- **接口说明**：老师获取某个学生的作答。
- **请求说明**：
  - 请求参数：
    - 路径参数： `id`：教师的唯一标识符（`Long` 类型）
    - 查询参数： `assignmentId`：作业的唯一标识符（`Long` 类型）
    - 查询参数： `studentId`：学生的唯一标识符（`Long` 类型）
  - 请求体： 无
- **响应说明**：
  - 响应格式：`JSON`
  - 成功响应（200 OK）：
    ```json
    {
      "message": "success",
      "totalScore": 100, // 总分
      "questions": [
        {
          "submissionAnswerId": "long",
          "body": "题干， 单题为空",
          "sequence": 12345, //题号
          "score": 4, //分数
          "subQuestions": [ //大题时不为空
            {    
              "submissionAnswerId": "long",
              "question": "问题内容", 
              "answer" : "答案", 
              "explanation": "解析", 
              "options": ["选项1", "选项2"], 
              "type" : "选择、填空、简答、作文",
    		  "subScore" : "int",//小题分数
              "studentAnswer": "string"
            },
            ...
          ],
          "question": "问题内容", //小题时不为空
          "answer" : "答案", //小题时不为空
          "explanation": "解析", //小题时不为空
          "options": ["选项1", "选项2"], //小题且为选择时不为空
          "type" : "选择、填空、简答、作文",//小题时不为空
          "studentAnswer": "string"
        },
        ...
      ]
    }
    ```
  - 失败响应（400 Bad Request）：
    ```json
    {
      "message": "获取失败",
      "assignmentId": null,
      "data": null
    }
    ```

### generate testPaper with types `finished`

- **接口路径**：`/api/teacher/generate-paper-with-types`

- **请求方法**：Post

- **接口说明**：教师生成试卷题目。

- **请求说明**

  - 请求头: `Content-Type` : `application/json`

  - 请求参数:

    - 请求体(`JSON` 格式)：

      ```json
      {
        "types":[
            {
               "type" : "题型",
               "number" : 2, //数量
            },
            ...
        ]
      }
      ```

- **响应说明**

  - **响应格式**：`JSON`

  - **成功响应** (`200 OK`):

    ```json
    {
      "message": "success",  // 响应的状态信息，表示操作是否成功
      "questions" : [
          {
              "id" : 123, //题目id
              "body": "题干",  // 响应的具体内容描述
              "diificulty" : 0.44,
              "subQuestions": [  // 包含多个子问题的大题部分
                {
                  "content": "大题子问题1",  // 内容
                  "answer": "子问题1的答案",  // 答案
                  "explanation": "子问题1的解释",  // 解释
                  "options": [],
                  "type": "",  // 题目类型
                  "knowledgePoint": "子问题1知识点"  // 子问题1的知识点
        		},
                  ...
      			]  
          },
                  ...
      ]
    }
    ```

  - **失败响应** (`400 Bad Request`):

    ```json
    {
      "message" : "错误",
    }
    ```

### Generate Exam Paper

- **接口路径**：`/api/teacher/paper/auto`

- **请求方法**：GET

- **接口说明**：教师用户自动生成试卷，返回题目信息。

- **响应说明**：e

  - 响应格式：`JSON`

  - **成功响应** (`200 OK`):

    ```json
    {
      "message": "试卷生成成功",
      "quesitons" : [ //积累与运用
          {
              "id" : 123, 
              "content": "问题",  
              "answer": "问题的答案",  
              "explanation": "问题的解释",  
              "options": [],
              "type": "",  // 题目类型
              "diificulty" : 0.44,
              "knowledgePoint": "问题知识点"
          },
          ...
      ],
      "bigQuestions":[ //后续大题
          {
    	      "id" : 123, 
              "body": "题干",  // 响应的具体内容描述
              "subQuestions": [  // 包含多个子问题的大题部分
                {
                  "content": "大题子问题1",  // 内容
                  "answer": "子问题1的答案",  // 答案
                  "explanation": "子问题1的解释",  // 解释
                  "options": [],
                  "type": "",  // 题目类型
    		     "diificulty" : 0.44,
                  "knowledgePoint": "子问题1知识点"  // 子问题1的知识点
        		},
                  ...
              ]
          },
                  ...
      ]
    }
    ```

  - **失败响应** (`400 Bad Request`):

    ```json
    {
      "message": "试卷生成失败",
    }
    ```

### Mark Submission `finished`

- **接口路径**：`/api/teacher/mark-submission`
- **请求方法**：`POST`
- **接口说明**：老师批改某个学生的作业。
- **请求说明**：
  - 请求头: `Content-Type` : `application/json`
  - 请求参数：
  - 请求体(`JSON` 格式)：
    ```json
    {
      "data" : [
        {
          "submissionAnswerId" : "long",
          "markScore" : "int",
          "feedback" : "string"
        },
        {
          "submissionAnswerId" : "long",
          "markScore" : "int",
          "feedback" : "string"
        },
        ...
      ]
    }
    ```
- **响应说明**：
  - 响应格式：`JSON`
  - 成功响应（200 OK）：
    ```json
    {
      "message": "success"
    }
    ```
  - 失败响应（400 Bad Request）：
    ```json
    {
      "message": "提交失败"
    }
    ```


### Get Class Knowledge Point Status

- **接口路径**：`/api/teacher/class/knowledge-point-status`
- **请求方法**：`POST`
- **接口说明**：老师获取班级知识点掌握情况。
- **请求说明**：
  - 请求头: `Content-Type` : `application/json`
  - 请求参数：
    - 查询参数：`classId`
  - 请求体(`JSON` 格式)：无
- **响应说明**：
  - 响应格式：`JSON`
  - 成功响应（200 OK）：
    ```json
    {
      "message": "success",
      "data": [
        {
          "name": "string",
          "score": 85.5
        },
        ...
      ]
    }
    ```
  - 失败响应（400 Bad Request）：
    ```json
    {
      "message": "获取失败",
      "data": null
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

### Get all courseStandard `finished`

- **接口路径**：`/api/system-admin/get-all-course-standards`

- **请求方法**：GET

- **接口说明**：系统管理员获取所有课标信息。

- **请求说明**：

  - 无请求参数。

- **响应说明**：

  - 响应格式：`JSON`

  - **成功响应** (`200 OK`):

    ```json
    {
      "message": "课标获取成功",
      "courseStandardInfos": [
        {
          "id": 12345,
          "title": "string",
          "executedDate": "string"
        },
        {
          "id": 12345,
          "title": "string",
          "executedDate": "string"
        },
        ...
      ]
    }
    ```

  - **失败响应** (`400 Bad Request`):

    ```json
    {
      "message": "知识点获取失败",
      "data": null
    }
    ```

### Create Course Standard `finished`

- **接口路径**：`/api/system-admin/create-course-standard`

- **请求方法**：POST

- **接口说明**：系统管理员创建新课标，传输课标 PDF 文件。

- **请求说明**：

  - 请求参数：
    - `executedDate` (必填) - 操作执行的时间。示例: `2024-11-11`
  
  - 请求体：
    - 文件 (`Multipart/Form-Data`)：`file` - 课标的 PDF 文件

- **响应说明**：

  - 响应格式：`JSON`

  - **成功响应** (`200 OK`):

    ```json
    {
      "message": "课标创建成功",
      "courseStandardId": 12345 // 新创建的课标ID
    }
    ```

  - **失败响应** (`400 Bad Request`):

    ```json
    {
      "message": "课标创建失败",
      "courseStandardId": null
    }
    ```



### Update Course Standard `finished`

- **接口路径**：`/api/system-admin/update-course-standard/{id}`

- **请求方法**：PUT

- **接口说明**：系统管理员更新指定的课标 PDF 文件。

- **请求说明**：

  - 请求参数：
    - 路径参数（Path Variable）：`id` - 课标的唯一标识符
    - `executedDate` (必填) - 操作执行的时间。
  
  - 请求体：
    - 文件 (`Multipart/Form-Data`)：`file` - 更新后的课标 PDF 文件

- **响应说明**：

  - 响应格式：`JSON`

  - **成功响应** (`200 OK`):

    ```json
    {
      "message": "课标更新成功"
    }
    ```

  - **失败响应** (`400 Bad Request`):

    ```json
    {
      "message": "课标更新失败"
    }
    ```

### Query Course Standard `finished`

- **接口路径**：`/api/system-admin/query-course-standard/{id}`
- **接口说明**：系统管理员查看课程标准（课标），以 PDF 文件形式返回。
- **请求说明**：

  - 路径参数：
    - `id`：课程的唯一标识符。
  - 请求体：无

- 响应说明：

  - 成功响应 (`200 OK`)

    - 响应类型为 `application/pdf`，返回 PDF 文件内容，附带 `Content-Disposition` 头以 inline 方式显示。

  - **失败响应** (`404 Not Found`):

    ```json
    响应体为空
    ```

### Delete Course Standard  `finished`

- **接口路径**：`/api/admin/delete-course-standard/{id}`

- **请求方法**：DELETE

- **接口说明**：系统管理员删除指定的课标。

- 请求说明：

  - 请求参数：
    - 路径参数（Path Variable）：`id` - 课标的唯一标识符

- 响应说明：

  - 响应格式：`JSON`

  - 成功响应(`200 OK`):

    ```
    {
      "message": "课标删除成功",
    }
    ```

    

  - 失败响应(`404 Not Found`):

    ```
    {
      "message": "课标删除失败",
    }
    ```

### Create Knowledge Point `finished`

- **接口路径**：`/api/system-admin/create-knowledge-point`
- **请求方法**：POST
- **接口说明**：系统管理员创建新知识点，传输知识点标题、描述以及对应的课标ID。
- **请求说明**：
  
  - 请求参数：无路径参数。
  - 请求体(`JSON` 格式)：
    ```json
    {
      "name": "string", // 知识点标题
      "description": "string", // 知识点描述
      "type": "string"
    }
    ```
- **响应说明**：
  
  - 响应格式：`JSON`
  - **成功响应** (`200 OK`):
    ```json
    {
      "message": "知识点创建成功",
      "knowledgePointId": 12345 // 新创建的知识点ID
    }
    ```
  - **失败响应** (`400 Bad Request`):
    
    ```json
    {
      "message": "知识点创建失败",
      "knowledgePointId": null
    }
    ```

### Delete Knowledge Point `finished`

- **接口路径**：`/api/system-admin/delete-knowledge-point/{id}`
- **请求方法**：DELETE
- **接口说明**：系统管理员删除指定的知识点。
- **请求说明**：
  - 请求参数：
    - 路径参数（Path Variable）：`id` - 知识点的唯一标识符
- **响应说明**：
  
  - 响应格式：`JSON`
  - **成功响应** (`200 OK`):
    
    ```json
    {
      "message": "知识点删除成功",
    }
    ```
  - **失败响应** (`400 Bad Request`):
    
    ```json
    {
      "message": "知识点删除失败",
    }
    ```

### Update Knowledge Point `finished`

- **接口路径**：`/api/system-admin/update-knowledge-point/{id}`
- **请求方法**：PUT
- **接口说明**：系统管理员更新指定的知识点信息。
- **请求说明**：
  
  - 请求参数：
    - 路径参数（Path Variable）：`id` - 知识点的唯一标识符
  - 请求体(`JSON` 格式)：
    ```json
    {
      "name": "string", // 更新后的知识点标题
      "description": "string", // 更新后的知识点描述
      "type": "string"
    }
    ```
- **响应说明**：
  - 响应格式：`JSON`
  - **成功响应** (`200 OK`):
    ```json
    {
      "message": "知识点更新成功",
    }
    ```
  - **失败响应** (`400 Bad Request`):
    ```json
    {
      "message": "知识点更新失败",
    }
    ```
### Get All Knowledge Points `finished`

- **接口路径**：`/api/system-admin/get-all-knowledge-points`

- **请求方法**：GET

- **接口说明**：系统管理员获取所有知识点信息。

- **请求说明**：
  - 无请求参数。

- **响应说明**：
  - 响应格式：`JSON`
  - **成功响应** (`200 OK`):
    ```json
    {
      "message": "知识点获取成功",
      "knowledgePointInfos": [
        {
          "id": 12345,
          "name": "string",
          "description": "string",
          "type" : "string"
        },
        {
          "id": 12345,
          "name": "string",
          "description": "string",
          "type" : "string"
        },
        ...
      ]
    }
    ```
  - **失败响应** (`400 Bad Request`):
    
    ```json
    {
      "message": "知识点获取失败",
      "data": null
    }
    ```

### Query Knowledge Point `finished`

- **接口路径**：`/api/system-admin/query-knowledge-point/{id}`
- **请求方法**：GET
- **接口说明**：系统管理员查询指定知识点信息。
- **请求说明**：
  
  - 请求参数：
    - 路径参数（Path Variable）：`id` - 知识点的唯一标识符
- **响应说明**：
  - 响应格式：`JSON`
  - **成功响应** (`200 OK`):
    ```json
    {
      "message": "知识点信息查询成功",
      "data": {
        "name": "string",
        "description": "string",
      }
    }
    ```
  - **失败响应** (`400 Bad Request`):
    ```json
    {
      "message": "知识点信息查询失败",
      "data": null
    }
    ```



### Get School Administrator Accounts `finished`

- **接口路径**：`/api/system-admin/get-school-admin-accounts`
- **请求方法**：GET
- **接口说明**：系统管理员获取所有学校管理员账号信息。
- **请求说明**：无
- **响应说明**：
  - 响应格式：`JSON`
  - **成功响应** (`200 OK`):
    
    ```json
    {
      "message": "学校管理员账号信息查询成功",
      "data": [
        {
          "schoolAdminId": 12345,
          "userName": "string",
          "email": "string",
          "schoolName": "string"
        },
        {
          "schoolAdminId": 12345,
          "userName": "string",
          "email": "string",
          "schoolName": "string"
        }
      ]
    }
    ```
  - **失败响应** (`400 Bad Request`):
    ```json
    {
      "message": "查询失败",
      "data": null
    }
    ```
    
    

### Creating School Administrator Account `finished`

- **接口路径**：`/api/system-admin/create-school-admin`

- **请求方法**：POST

- **接口说明**：系统管理员可以通过此接口创建一个新的学校管理员账号。

- **请求说明**：

  - **请求体**：`JSON`
    
    ```json
    {
        "name" :  "用户名",
        "password" : "密码",
        "schoolId" : "学校id"
    }
    ```

- **响应说明**：
  
  - **响应格式**：JSON
  
  - **成功响应** （200 OK）：
    ```json
    {
      "message": "账号生成成功",
    }
    ```
    
  - **失败响应** （400 Bad Request）：
    ```json
    {
      "message": "账号生成失败",
    }
    ```



### Delete School Administrator Account `finished`

- **接口路径**：`/api/system-admin/delete-school-admin-account/{id}`
- **请求方法**：DELETE
- **接口说明**：系统管理员删除指定学校管理员账号。
- **请求说明**：
  - 请求参数：
    - 路径参数（Path Variable）：`id` - 学校管理员唯一标识符
- **响应说明**：
  - 响应格式：`JSON`
  - **成功响应** (`200 OK`):
    ```json
    {
      "message": "删除成功"
    }
    ```
  - **失败响应** (`400 Bad Request`):
    ```json
    {
      "message": "删除失败"
    }
    ```




### Change Password `finished`

- **接口路径**：`/api/system-admin/{id}/change-password`
- **请求方法**：POST
- **接口说明**：系统管理员账号修改密码。
- **请求说明**
  - 请求头: `Content-Type` : `application/json`
  - 请求参数:
    - 路径参数（Path Variable）：`id` - 系统管理员的唯一标识符
    - 请求体(`JSON` 格式)：
    ```json
    {
      "password" : "string", // 旧密码
      "newPassword" : "string"
    }
    ```
- **响应说明**
  - 响应格式: `JSON`
  - **成功响应** (`200 OK`):
    ```json
    {
      "message" : "success"
    }
    ```
  - **失败响应** (`401 Unauthorized`):
    ```json
    {
      "message" : "修改密码失败"
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
          "schoolName": "string",
          "authorizationCode": "string"
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

### Create Teacher

- **接口路径**：`/api/school-admin/create-teacher`
- **请求方法**：POST
- **接口说明**：学校管理员创建新老师账号，传输老师姓名、邮箱、密码、手机号以及学校ID。
- **请求说明**：
  - 请求参数：无路径参数。
  - 请求体(`JSON` 格式)：
    ```json
    {
      "name": "string", // 老师姓名
      "email": "string", // 老师邮箱
      "password": "string", // 老师密码
      "phone_number": "string", // 老师手机号
      "school_id": "bigint" // 学校ID
    }
    ```
- **响应说明**：
  - 响应格式：`JSON`
  - **成功响应** (`200 OK`):
    ```json
    {
      "message": "老师账号创建成功",
      "data": {
        "id": "bigint" // 新创建的老师账号ID
      }
    }
    ```
  - **失败响应** (`400 Bad Request`):
    ```json
    {
      "message": "老师账号创建失败",
      "data": null
    }
    ```

### Delete Teacher `finished`

- **接口路径**：`/api/school-admin/{id}/delete-teacher/{teacherId}`
- **请求方法**：DELETE
- **接口说明**：学校管理员删除指定的老师账号。
- **请求说明**：
  - 请求参数：
    - 路径参数（Path Variable）：`id` - 学校管理员账号的唯一标识符
    - 路径参数（Path Variable）：`teacherId` - 教师账号的唯一标识符
- **响应说明**：
  - 响应格式：`JSON`
  - **成功响应** (`200 OK`):
    ```json
    {
      "message": "教师账号删除成功"
    }
    ```
  - **失败响应** (`400 Bad Request`):
    ```json
    {
      "message": "教师账号删除失败"
    }
    ```

### Query Teacher `finished`

- **接口路径**：`/api/school-admin/{id}/query-teacher/{teacherId}`
- **请求方法**：GET
- **接口说明**：学校管理员查询指定老师账号信息。
- **请求说明**：
  - 请求参数：
    - 路径参数（Path Variable）：`id` - 学校管理员账号的唯一标识符
    - 路径参数（Path Variable）：`teacherId` - 老师账号的唯一标识符
- **响应说明**：
  - 响应格式：`JSON`
  - **成功响应** (`200 OK`):
    ```json
    {
      "message": "老师账号信息查询成功",
      "data": {
        "id": "bigint",
        "name": "string",
        "email": "string",
        "phoneNumber": "string",
        "schoolId": "bigint"
      }
    }
    ```
  - **失败响应** (`400 Bad Request`):
    ```json
    {
      "message": "老师账号信息查询失败",
      "data": null
    }
    ```

### Create Student

- **接口路径**：`/api/school-admin/create-student`
- **请求方法**：POST
- **接口说明**：学校管理员创建新学生账号，传输学生用户名、邮箱、密码、姓名、年级以及学校ID。
- **请求说明**：
  - 请求参数：无路径参数。
  - 请求体(`JSON` 格式)：
    ```json
    {
      "username": "string", // 学生用户名
      "email": "string", // 学生邮箱
      "password": "string", // 学生密码
      "name": "string", // 学生姓名
      "grade": "int", // 学生年级
      "school_id": "bigint" // 学校ID
    }
    ```
- **响应说明**：
  - 响应格式：`JSON`
  - **成功响应** (`200 OK`):
    ```json
    {
      "message": "学生账号创建成功",
      "data": {
        "id": "bigint" // 新创建的学生账号ID
      }
    }
    ```
  - **失败响应** (`400 Bad Request`):
    ```json
    {
      "message": "学生账号创建失败",
      "data": null
    }
    ```

### Delete Student `finished`

- **接口路径**：`/api/school-admin/{id}/delete-student/{studentId}`
- **请求方法**：DELETE
- **接口说明**：学校管理员删除指定的学生账号。
- **请求说明**：
  - 请求参数：
    - 路径参数（Path Variable）：`id` - 学校管理员账号的唯一标识符
    - 路径参数（Path Variable）：`studentId` - 学生账号的唯一标识符
- **响应说明**：
  - 响应格式：`JSON`
  - **成功响应** (`200 OK`):
    ```json
    {
      "message": "学生账号删除成功"
    }
    ```
  - **失败响应** (`400 Bad Request`):
    ```json
    {
      "message": "学生账号删除失败"
    }
    ```

### Query Student `finished`

- **接口路径**：`/api/school-admin/{id}/query-student/{studentId}`
- **请求方法**：GET
- **接口说明**：学校管理员查询指定学生账号信息。
- **请求说明**：
  - 请求参数：
    - 路径参数（Path Variable）：`id` - 学校管理员账号的唯一标识符
    - 路径参数（Path Variable）：`studentId` - 学生账号的唯一标识符
- **响应说明**：
  - 响应格式：`JSON`
  - **成功响应** (`200 OK`):
    ```json
    {
      "message": "学生账号信息查询成功",
      "data": {
        "id": "bigint",
        "username": "string",
        "email": "string",
        "name": "string",
        "grade": "int",
        "schoolId": "bigint"
      }
    }
    ```
  - **失败响应** (`400 Bad Request`):
    ```json
    {
      "message": "学生账号信息查询失败",
      "data": null
    }
    ```

### Generate Authorization Code `finished`

- **接口路径**：`/api/school-admin/{id}/generate-authorization-code`
- **请求方法**：GET
- **接口说明**：学校管理员生成授权码，用于授权用户访问特定资源。(无授权码时生成，已有时更新)
- **请求说明**：
  - 请求参数：
   - 路径参数（Path Variable）：`id` - 学校管理员账号的唯一标识符
- **响应说明**：
  - 响应格式：`JSON`
  - **成功响应** (`200 OK`):
    ```json
    {
      "message": "授权码生成成功",
      "code": "string", // 生成的授权码
    }
    ```
  - **失败响应** (`400 Bad Request`):
    ```json
    {
      "message": "授权码生成失败",
      "code": null
    }
    ```

### Query All Students `finished`

- **接口路径**：`/api/school-admin/{id}/query-all-students`
- **请求方法**：GET
- **接口说明**：学校管理员查询校内学生账号信息。
- **请求说明**：
  - 请求参数：
    - 路径参数（Path Variable）：`id` - 学校管理员账号的唯一标识符
- **响应说明**：
  - 响应格式：`JSON`
  - **成功响应** (`200 OK`):
    ```json
    {
      "message": "学生账号信息查询成功",
      "data": [
        {
        "id": "bigint",
        "username": "string",
        "email": "string",
        "name": "string",
        "grade": "int",
        "schoolId": "bigint"
         },
         {
         "id": "bigint",
         "username": "string",
         "email": "string",
         "name": "string",
         "grade": "int",
         "schoolId": "bigint"
         },
        ...
      ]
    }
    ```
  - **失败响应** (`400 Bad Request`):
    ```json
    {
      "message": "学生账号信息查询失败",
      "data": null
    }
    ```

### Query All Teachers `finished`

- **接口路径**：`/api/school-admin/{id}/query-all-teachers`
- **请求方法**：GET
- **接口说明**：学校管理员查询校内教师账号信息。
- **请求说明**：
  - 请求参数：
    - 路径参数（Path Variable）：`id` - 学校管理员账号的唯一标识符
- **响应说明**：
  - 响应格式：`JSON`
  - **成功响应** (`200 OK`):
    ```json
    {
      "message": "教师账号信息查询成功",
      "data": [
        {
        "id": "bigint",
        "email": "string",
        "name": "string",
        "phoneNumber": "string",
        "schoolId": "bigint"
         },
         {
         "id": "bigint",
        "email": "string",
        "name": "string",
        "phoneNumber": "string",
        "schoolId": "bigint"
         },
        ...
      ]
    }
    ```
  - **失败响应** (`400 Bad Request`):
    ```json
    {
      "message": "教师账号信息查询失败",
      "data": null
    }
    ```

### Send Verification Code `finished`

- **接口路径**：`/api/school-admin/{id}/send-verification-code`
- **请求方法**：POST
- **接口说明**：学校管理员用户通过邮箱发送验证码。
- **请求说明**
  
  - 请求头: `Content-Type` : `application/json`
  - 请求参数:
    - 请求体(`JSON` 格式)：
    ```json
    {
      "email" : "string" // 学校管理员的邮箱地址
    }
    ```
- **响应说明**
  - 响应格式: `JSON`
  - **成功响应** (`200 OK`):
    ```json
    {
      "message" : "验证码已发送",
      "verificationCode" : "string",
    }
    ```
  - **失败响应** (`400 Bad Request`):
    ```json
    {
      "message" : "String", 
      "verificationCode" : null
    }
    ```

### Bind Email `finished`

- **接口路径**：`/api/school-admin/{id}/bind-email`
- **请求方法**：POST
- **接口说明**：学校管理员用户绑定邮箱。
- **请求说明**
  
  - 请求头: `Content-Type` : `application/json`
  - 请求参数:
    - 路径参数：`id`  学校管理员的id
    - 请求体(`JSON` 格式)：
    ```json
    {
      "email" : "string" // 教师的邮箱地址
    }
    ```
- **响应说明**
  - 响应格式: `JSON`
  - **成功响应** (`200 OK`):
    ```json
    {
      "message" : "成功",
    }
    ```
  - **失败响应** (`400 Bad Request`):
    
    ```json
    {
      "message" : "String", // 授权码不可用 or 邮箱已注册 
    }
    ```

### Update Username  `finished`

- **接口路径**：`/api/school-admin/{id}/update-username`
- **请求方法**：PUT
- **接口说明**：学校管理员账号修改用户名。
- **请求说明**
  
  - 请求头: `Content-Type` : `application/json`
  - 请求参数:
    - 路径参数（Path Variable）：`id` - 学校管理员的唯一标识符
    - 请求体(`JSON` 格式)：
    ```json
    {
      "username" : "string"
    }
    ```
- **响应说明**
  - 响应格式: `JSON`
  - **成功响应** (`200 OK`):
    ```json
    {
      "message" : "success"
    }
    ```
  - **失败响应** (`400 Bad Request`):
    ```json
    {
      "message" : "string"
    }
    ```

### Update Name `finished`

- **接口路径**：`/api/school-admin/{id}/update-name`
- **请求方法**：PUT
- **接口说明**：学校管理员账号修改负责人名字。
- **请求说明**
  - 请求头: `Content-Type` : `application/json`
  - 请求参数:
    - 路径参数（Path Variable）：`id` - 学校管理员的唯一标识符
    - 请求体(`JSON` 格式)：
    ```json
    {
      "name" : "string"
    }
    ```
- **响应说明**
  - 响应格式: `JSON`
  - **成功响应** (`200 OK`):
    ```json
    {
      "message" : "success"
    }
    ```
  - **失败响应** (`400 Bad Request`):
    ```json
    {
      "message" : "string"
    }
    ```

### Change Password `finished`

- **接口路径**：`/api/school-admin/{id}/change-password`
- **请求方法**：POST
- **接口说明**：学校管理员账号修改密码。
- **请求说明**
  - 请求头: `Content-Type` : `application/json`
  - 请求参数:
    - 路径参数（Path Variable）：`id` - 学校管理员的唯一标识符
    - 请求体(`JSON` 格式)：
    ```json
    {
      "password" : "string", // 旧密码
      "newPassword" : "string"
    }
    ```
- **响应说明**
  - 响应格式: `JSON`
  - **成功响应** (`200 OK`):
    ```json
    {
      "message" : "success"
    }
    ```
  - **失败响应** (`401 Unauthorized`):
    ```json
    {
      "message" : "修改密码失败"
    }
    ```

### Get All Classes `finished`

- **接口路径**：`/api/school-admin/{id}/get-classes`
- **请求方法**：GET
- **接口说明**：通过学校管理员ID获取此学校所有班级。

- **请求说明**：
  - **请求参数**：
    - **路径参数**: `id`：学校管理员ID，路径参数。
  - **请求体**: 无
- **响应说明**：
  - **响应格式**：JSON
  - **成功响应**（200 OK）：
    ```json
    {
      "message": "获取班级成功",
      "data": [
        {
          "classId": "number", // 对应数据库中的 id 字段
          "name": "string", // 对应数据库中的 name 字段
          "teacherName": "string",// 对应数据库中的 description 字段
          "inviteCode": "string" // 对应数据库中的 inviteCode 字段
        },
        {
          "classId": "number", // 对应数据库中的 id 字段
          "name": "string", // 对应数据库中的 name 字段
          "teacherName": "string",// 对应数据库中的 description 字段
          "inviteCode": "string" // 对应数据库中的 inviteCode 字段
        },
        ...
      ]
    }
    ```
  - **失败响应**（400 Bad Request）：
    ```json
    {
      "message": "班级获取失败",
      "data": null
    }
    ```

### Query Class Details `finished`

- **接口路径**：`/api/school-admin/{id}/query-class`
- **请求方法**：GET
- **接口说明**：获取班级详细信息，包括班级中的小组和成员。

- **请求说明**：
  - **请求参数**：
    - **路径参数**: `id`：学校管理员ID，路径参数。
    - **查询参数**: `classId`：班级ID，查询参数。

- **响应说明**：
  - **响应格式**：JSON
  - **成功响应**（200 OK）：
    ```json
    {
      "message": "获取班级详细信息成功",
      "data": {
        "className": "string", // 对应数据库中的 name 字段
        "classDescription": "string",
        "groups": [
          {
            "groupId": "number", // 
            "groupName": "string", // 
            "groupDescription": "string"// 对应数据库中的 description 字段
          },
          {
            "groupId": "number", // 
            "groupName": "string", // 
            "groupDescription": "string"// 对应数据库中的 description 字段
          },
          ...
        ],
        "students": [
          {
            "studentId": "number", // 成员ID
            "studentName": "string" // 成员名称
          },
          {
            "studentId": "number", // 成员ID
            "studentName": "string" // 成员名称
          },
          ...
        ]
      }
    }
    ```
  - **失败响应**（400 Bad Request）：
    ```json
    {
      "message": "班级详细信息获取失败",
      "data": null
    }
    ```

### Create Manager `finished`

- **接口路径**：`/api/school-admin/create-manager`

- **请求方法**：POST

- **接口说明**：学校管理员用邮箱和密码给审核老师创建账号。

- **请求说明**

  - 请求头: `Content-Type` : `application/json`

  - 请求参数:

    - 请求体(`JSON` 格式)：

    ```json
    {
      "email" : "string", // 
      "password" : "string"
    }
    ```

- **响应说明**

  - 响应格式: `JSON`

  - **成功响应** (`200 OK`):

    ```json
    {
      "message" : "创建成功"
    }
    ```

  - **失败响应** (`400 Bad Request`):

    ```json
    {
      "message" : "邮箱已注册" //or  "用户名已存在"
    }
    ```


## Image

### 图片上传接口

- **接口路径**：`/api/uploads/image`
- **请求方法**：POST
- **接口说明**：上传图片，支持头像和内容两种类型的图片上传。
- **请求说明**：
  - 请求参数：无路径参数。
  - 请求体(`multipart/form-data` 格式)：
    ```bash
    {
      "image": <file>,   // 上传的图片文件
      "type": "string"   // 图片类型，取值："avatar"（头像）或 "content"（内容）
    }
    ```
- **响应说明**：
  - 响应格式：`JSON`
  - **成功响应** (`200 OK`):
    ```json
    {
      "imageUrl": "/uploads/avatar/9a3ed290-bc68-4bff-bef2-4c71f774d07b-image.jpg"
    }
    ```
  - **失败响应** (`400 Bad Request`):
    ```json
    {
      "message": "Invalid image type. Must be 'avatar' or 'content'."
    }
    ```

---

### 获取图片接口

- **接口路径**：`/api/uploads/images/{type}/{imageName}`
- **请求方法**：GET
- **接口说明**：根据类型和图片名获取已上传的图片。
- **请求说明**：
  
  - 请求参数：路径参数
    - `type`：图片类型，取值：`avatar` 或 `content`
    - `imageName`：图片文件名
  - 请求示例：
    ```bash
    GET /api/uploads/images/content/a9a3ed290-bc68-4bff-bef2-4c71f774d07b-image.jpg
    ```
- **响应说明**：
  - 响应格式：图片文件
  - **成功响应** (`200 OK`):
    - 返回图片文件，具体类型由图片扩展名决定（如 JPEG、PNG）。
  - **失败响应** (`404 Not Found`):
    ```json
    {
      "message": "Image not found."
    }
    ```

### 删除图片接口

- **接口路径**：`/api/uploads/image/{type}/{imageName}`

- **请求方法**：`DELETE`

- **接口说明**：根据图片类型和图片名删除已上传的图片。

- **请求说明**：

  - 请求参数：路径参数

    - `type`：图片类型，取值：`avatar` 或 `content`。
    - `imageName`：图片文件名。

  - 请求示例：

    ```html
    DELETE /api/uploads/image/content/a9a3ed290-bc68-4bff-bef2-4c71f774d07b-image.jpg
    ```

- **响应说明**：

  - **成功响应** (`200 OK`):

    ```json
    {
      "message": "File deleted successfully."
    }
    ```

  - **失败响应** (`404 Not Found`):

    ```json
    {
      "message": "File not found."
    }
    ```

  - **服务器错误响应** (`500 Internal Server Error`):

    ```json
    {
      "message": "Failed to delete the file."
    }
    ```
