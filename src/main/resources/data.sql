INSERT INTO USER
VALUES (100, 'ADMIN', 'ADMINP@SS'),
       (200, 'Edgar', 'P@wP@w'),
       (300, 'Bernard', 'Sh@wSh@w'),
       (400, 'John', 'D0wD0w');

INSERT INTO SESSION
VALUES (11, 100, sysdate + 11 / 24),
       (12, 200, sysdate + 12 / 24),
       (13, 300, sysdate + 13 / 24),
       (14, 400, sysdate + 14 / 24),
       (21, 100, sysdate + 21 / 24),
       (22, 200, sysdate + 22 / 24),
       (23, 300, sysdate + 23 / 24),
       (24, 400, sysdate + 24 / 24);