CREATE TABLE Daily (
    Id INTEGER PRIMARY KEY AUTOINCREMENT,
    Theme varchar(200)  NULL,
    Cost float  NOT NULL,
    AddTime timestamp  NULL,
    Remark varchar(250)  NULL,
    FinanceType integer  NULL,
    CreateBy varchar(50)  NULL,
    LastUpdateBy varchar(50)  NULL,
    LastUpdateDate timestamp  NULL,
    ForDate timestamp  NULL,
    ProjectId integer,
);

CREATE TABLE User (
    UserId varchar(80)  NOT NULL,
    Password varchar(80)  NULL
);

CREATE TABLE Dict (
    DictID integer  NOT NULL,
    Code varchar(50)  NULL,
    DictName nvarchar(100)  NULL,
    Remark nvarchar(500)  NULL,
    IsEnable boolean  NULL,
    CreateBy varchar(50)  NULL,
    CreateDate timestamp  NULL,
    CreatorName nvarchar(100)  NULL,
    LastUpdateBy varchar(50)  NULL,
    LastUpdateByName nvarchar(100)  NULL,
    LastUpdateDate timestamp  NULL
);

CREATE TABLE DictItems (
    DictItemID integer  NOT NULL,
    DictCode varchar(50)  NULL,
    ItemCode varchar(50)  NULL,
    ItemName nvarchar(100)  NULL,
    ItemValue nvarchar(200)  NULL,
    Remark nvarchar(500)  NULL,
    IsEnable boolean  NULL,
    CreateBy varchar(50)  NULL,
    CreatorName nvarchar(100)  NULL,
    CreateDate timestamp  NULL,
    LastUpdateBy varchar(50)  NULL,
    LastUpdateByName nvarchar(100)  NULL,
    LastUpdateDate timestamp  NULL
);

CREATE TABLE Project (
    Id INTEGER PRIMARY KEY AUTOINCREMENT,
    Name nvarchar(2000)  NOT NULL,
    Remark nvarchar(2000)  NULL
);