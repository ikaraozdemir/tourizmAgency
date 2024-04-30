PGDMP     ,                    |            turizmagency    15.6    15.6 ,    8           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            9           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            :           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            ;           1262    16398    turizmagency    DATABASE     �   CREATE DATABASE turizmagency WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Turkish_T�rkiye.1254';
    DROP DATABASE turizmagency;
                postgres    false            �            1259    16399    hotel    TABLE     �   CREATE TABLE public.hotel (
    hotel_id integer NOT NULL,
    hotel_city text,
    hotel_region text,
    hotel_name text,
    hotel_phno text,
    hotel_mail text,
    hotel_star text,
    hotel_address text
);
    DROP TABLE public.hotel;
       public         heap    postgres    false            �            1259    16406    hotel_features    TABLE     �   CREATE TABLE public.hotel_features (
    hotel_features_id integer NOT NULL,
    hotel_features text,
    hotel_features_hotel_id integer
);
 "   DROP TABLE public.hotel_features;
       public         heap    postgres    false            �            1259    16457 $   hotel_features_hotel_features_id_seq    SEQUENCE     �   ALTER TABLE public.hotel_features ALTER COLUMN hotel_features_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.hotel_features_hotel_features_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    215            �            1259    16456    hotel_hotel_id_seq    SEQUENCE     �   ALTER TABLE public.hotel ALTER COLUMN hotel_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.hotel_hotel_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    214            �            1259    16413    pension    TABLE     w   CREATE TABLE public.pension (
    pension_id integer NOT NULL,
    pension_hotel_id integer,
    pension_types text
);
    DROP TABLE public.pension;
       public         heap    postgres    false            �            1259    16458    pension_pension_id_seq    SEQUENCE     �   ALTER TABLE public.pension ALTER COLUMN pension_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.pension_pension_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    216            �            1259    16420    reservation    TABLE     �  CREATE TABLE public.reservation (
    reserv_id integer NOT NULL,
    reserv_room_id integer,
    adult_guest_count integer,
    child_guest_count integer,
    reserv_guest_idno text,
    reserv_guest_name text,
    reserv_guest_mpno text,
    reserv_guest_mail text,
    reserv_total_prc integer,
    reserv_note text,
    checkin_date date,
    checkout_date date,
    reserv_hotel_id integer
);
    DROP TABLE public.reservation;
       public         heap    postgres    false            �            1259    16459    reservation_reserv_id_seq    SEQUENCE     �   ALTER TABLE public.reservation ALTER COLUMN reserv_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.reservation_reserv_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    217            �            1259    16427    room    TABLE       CREATE TABLE public.room (
    room_id integer NOT NULL,
    room_hotel_id integer NOT NULL,
    room_season_id integer NOT NULL,
    room_pension_id integer NOT NULL,
    room_stock integer NOT NULL,
    prc_for_child integer,
    prc_for_adult integer,
    room_type text
);
    DROP TABLE public.room;
       public         heap    postgres    false            �            1259    16434    room_features    TABLE     �   CREATE TABLE public.room_features (
    room_feature_id integer NOT NULL,
    room_feature_room_id integer NOT NULL,
    feature_name text,
    feature_value text
);
 !   DROP TABLE public.room_features;
       public         heap    postgres    false            �            1259    16460 !   room_features_room_feature_id_seq    SEQUENCE     �   ALTER TABLE public.room_features ALTER COLUMN room_feature_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.room_features_room_feature_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    219            �            1259    16462    room_room_id_seq    SEQUENCE     �   ALTER TABLE public.room ALTER COLUMN room_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.room_room_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    218            �            1259    16441    season    TABLE     �   CREATE TABLE public.season (
    season_id integer NOT NULL,
    season_hotel_id integer,
    season_start date,
    season_end date,
    season_name text
);
    DROP TABLE public.season;
       public         heap    postgres    false            �            1259    16461    season_season_id_seq    SEQUENCE     �   ALTER TABLE public.season ALTER COLUMN season_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.season_season_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    220            �            1259    16448    user    TABLE     �   CREATE TABLE public."user" (
    user_id integer NOT NULL,
    user_role text NOT NULL,
    user_name text NOT NULL,
    user_pw text NOT NULL
);
    DROP TABLE public."user";
       public         heap    postgres    false            �            1259    16455    user_user_id_seq    SEQUENCE     �   ALTER TABLE public."user" ALTER COLUMN user_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.user_user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    221            &          0    16399    hotel 
   TABLE DATA           �   COPY public.hotel (hotel_id, hotel_city, hotel_region, hotel_name, hotel_phno, hotel_mail, hotel_star, hotel_address) FROM stdin;
    public          postgres    false    214   u3       '          0    16406    hotel_features 
   TABLE DATA           d   COPY public.hotel_features (hotel_features_id, hotel_features, hotel_features_hotel_id) FROM stdin;
    public          postgres    false    215   �4       (          0    16413    pension 
   TABLE DATA           N   COPY public.pension (pension_id, pension_hotel_id, pension_types) FROM stdin;
    public          postgres    false    216   �5       )          0    16420    reservation 
   TABLE DATA           �   COPY public.reservation (reserv_id, reserv_room_id, adult_guest_count, child_guest_count, reserv_guest_idno, reserv_guest_name, reserv_guest_mpno, reserv_guest_mail, reserv_total_prc, reserv_note, checkin_date, checkout_date, reserv_hotel_id) FROM stdin;
    public          postgres    false    217   B6       *          0    16427    room 
   TABLE DATA           �   COPY public.room (room_id, room_hotel_id, room_season_id, room_pension_id, room_stock, prc_for_child, prc_for_adult, room_type) FROM stdin;
    public          postgres    false    218   �6       +          0    16434    room_features 
   TABLE DATA           k   COPY public.room_features (room_feature_id, room_feature_room_id, feature_name, feature_value) FROM stdin;
    public          postgres    false    219   �7       ,          0    16441    season 
   TABLE DATA           c   COPY public.season (season_id, season_hotel_id, season_start, season_end, season_name) FROM stdin;
    public          postgres    false    220   #:       -          0    16448    user 
   TABLE DATA           H   COPY public."user" (user_id, user_role, user_name, user_pw) FROM stdin;
    public          postgres    false    221   �:       <           0    0 $   hotel_features_hotel_features_id_seq    SEQUENCE SET     T   SELECT pg_catalog.setval('public.hotel_features_hotel_features_id_seq', 130, true);
          public          postgres    false    224            =           0    0    hotel_hotel_id_seq    SEQUENCE SET     A   SELECT pg_catalog.setval('public.hotel_hotel_id_seq', 20, true);
          public          postgres    false    223            >           0    0    pension_pension_id_seq    SEQUENCE SET     F   SELECT pg_catalog.setval('public.pension_pension_id_seq', 149, true);
          public          postgres    false    225            ?           0    0    reservation_reserv_id_seq    SEQUENCE SET     G   SELECT pg_catalog.setval('public.reservation_reserv_id_seq', 7, true);
          public          postgres    false    226            @           0    0 !   room_features_room_feature_id_seq    SEQUENCE SET     Q   SELECT pg_catalog.setval('public.room_features_room_feature_id_seq', 311, true);
          public          postgres    false    227            A           0    0    room_room_id_seq    SEQUENCE SET     ?   SELECT pg_catalog.setval('public.room_room_id_seq', 60, true);
          public          postgres    false    229            B           0    0    season_season_id_seq    SEQUENCE SET     C   SELECT pg_catalog.setval('public.season_season_id_seq', 74, true);
          public          postgres    false    228            C           0    0    user_user_id_seq    SEQUENCE SET     >   SELECT pg_catalog.setval('public.user_user_id_seq', 5, true);
          public          postgres    false    222            �           2606    16412 "   hotel_features hotel_features_pkey 
   CONSTRAINT     o   ALTER TABLE ONLY public.hotel_features
    ADD CONSTRAINT hotel_features_pkey PRIMARY KEY (hotel_features_id);
 L   ALTER TABLE ONLY public.hotel_features DROP CONSTRAINT hotel_features_pkey;
       public            postgres    false    215            �           2606    16405    hotel hotel_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.hotel
    ADD CONSTRAINT hotel_pkey PRIMARY KEY (hotel_id);
 :   ALTER TABLE ONLY public.hotel DROP CONSTRAINT hotel_pkey;
       public            postgres    false    214            �           2606    16419    pension pension_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public.pension
    ADD CONSTRAINT pension_pkey PRIMARY KEY (pension_id);
 >   ALTER TABLE ONLY public.pension DROP CONSTRAINT pension_pkey;
       public            postgres    false    216            �           2606    16426    reservation reservation_pkey 
   CONSTRAINT     a   ALTER TABLE ONLY public.reservation
    ADD CONSTRAINT reservation_pkey PRIMARY KEY (reserv_id);
 F   ALTER TABLE ONLY public.reservation DROP CONSTRAINT reservation_pkey;
       public            postgres    false    217            �           2606    16440     room_features room_features_pkey 
   CONSTRAINT     k   ALTER TABLE ONLY public.room_features
    ADD CONSTRAINT room_features_pkey PRIMARY KEY (room_feature_id);
 J   ALTER TABLE ONLY public.room_features DROP CONSTRAINT room_features_pkey;
       public            postgres    false    219            �           2606    16433    room room_pkey 
   CONSTRAINT     Q   ALTER TABLE ONLY public.room
    ADD CONSTRAINT room_pkey PRIMARY KEY (room_id);
 8   ALTER TABLE ONLY public.room DROP CONSTRAINT room_pkey;
       public            postgres    false    218            �           2606    16447    season season_pkey 
   CONSTRAINT     W   ALTER TABLE ONLY public.season
    ADD CONSTRAINT season_pkey PRIMARY KEY (season_id);
 <   ALTER TABLE ONLY public.season DROP CONSTRAINT season_pkey;
       public            postgres    false    220            �           2606    16454    user user_pkey 
   CONSTRAINT     S   ALTER TABLE ONLY public."user"
    ADD CONSTRAINT user_pkey PRIMARY KEY (user_id);
 :   ALTER TABLE ONLY public."user" DROP CONSTRAINT user_pkey;
       public            postgres    false    221            &   K  x�U��n�0���S�^�HH�Щ�
u(UխR���������@��k�tbK�^8
H�N����߆�'ה.	S^f�V�LZĄ���y|3�c;�L���n�i.��*K���۪B��k�Q�\�)��ϲ,
C X�JH��y**|mk�u���Й�,�e�z�Td>D��2��$��ۦ�'�e���dIJJ���������L��\=�3F ����&��T��BnH�� �����D��|�nk?�X�\,�#0�g����I�ߴٝ�����s�6qךpu�g@0b}Z�ű�����o*���	�����eY{�%�a      '   �   x�m�A� E��)8�J[�]�1.M\��i�D-hMz���I7��"y�y_�?�E����7��ޙ� �`��;<����	fl�}���[�v����/z�EʬB���	��LTEf2�5�B�B��M8ï�2+�9�ܡ���T�r�is���@I�3�1�F{E˒�!�:$�E�"�'�O�X� j����� ��%��      (   �   x�3�4��H-R8:?�R!%1#3��(�Xtdc�BAb^qfe~�1P,$I��؄��]���HM�9��9�J��J�BvbFYbNɑ�@AK� ��& aTSM9-0L51���:�S�	H�*S�0�1z\\\ ��[�      )   y   x�3�4��4�4�43715361646�J��K-P�<�1'7��S��@(�`fa�`na`j�YV�P�	��K���42600��H-�H�K�T��/)�U����4202�50�50�38-�b���� mk �      *   �   x�e�Mj�@���a��w���R\JI�+�����$�p0�}ғ��?��Z��r����(u]w�SǬ�R/�B �zM�S���:��R/nd`�%�`��A�N�-��N;�#}����Qf����㼾��X_�����{:�a�U��h�K�=9��������/�u�y��ݓ�Ȟ�����@�=��s��@�̈��m\�["6m�7�\�u4�d���߷i��_�j�      +   U  x�}U;��0��S��4��-�]	� @�4
����lo k�ˌ!Q�E����=��J|��o����������r�����ãвӠ��~���_����o�Bu�o�ſ��Y,��s����՟n��2�����<a�͕��+%L�^i�KeP��zJp@E����W"�cB�:'h	4O�J���5�e�6(	�b=%8�"��L�+	�1!a��*`asrPD**)��yV��n>F	ۘ����6#C"{[���	ۘ�=5�@"{G�������TT��"�OW���Uo�3����� ���J�����p>�*h.���4'I�iNiN�4OsQ��/�\����%�1�+��&�{��a�����D���"��\��EV0,}9_����z<e{{r�p��m~dt�TT^�H�ME�rh�}y���2̨H��QMGC\dEt�~�xp�Qq�e@x9+Ͳ"q��n���X�n�&EE�h�&�?u�hJO��HO����&{r�)t�~O7���f��aSFJ��&�D;��������iHdoKTz;��������0�$�w$��9q���:%��~�n��Ϗ]���Lt�      ,   W   x�3�4�4202�50"(�T�ؐ3��ƣ���9��@�0��04��L��2��44�k��B6�9-��qZ⑏���� �1      -   @   x�3�tt����t)�M�S8<�*%57����Șˈ��7��?�Օ3�(5W��6����)W� D@     