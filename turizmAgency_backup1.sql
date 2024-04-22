PGDMP  1                     |            turizmagency    16.2    16.2 ,    �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            �           1262    49346    turizmagency    DATABASE     �   CREATE DATABASE turizmagency WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'English_United Kingdom.1254';
    DROP DATABASE turizmagency;
                postgres    false            �            1259    49350    hotel    TABLE     �   CREATE TABLE public.hotel (
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
       public         heap    postgres    false            �            1259    49353    hotel_features    TABLE     �   CREATE TABLE public.hotel_features (
    hotel_features_id integer NOT NULL,
    hotel_features_hotel_id integer NOT NULL,
    hotel_features text
);
 "   DROP TABLE public.hotel_features;
       public         heap    postgres    false            �            1259    49377    hotel_hotel_id_seq    SEQUENCE     �   ALTER TABLE public.hotel ALTER COLUMN hotel_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.hotel_hotel_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    216            �            1259    49383    hotel_props_hotel_props_id_seq    SEQUENCE     �   ALTER TABLE public.hotel_features ALTER COLUMN hotel_features_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.hotel_props_hotel_props_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    217            �            1259    49359    pension    TABLE     �   CREATE TABLE public.pension (
    pension_id integer NOT NULL,
    pension_hotel_id integer NOT NULL,
    pension_types text
);
    DROP TABLE public.pension;
       public         heap    postgres    false            �            1259    49395    pension_pension_id_seq    SEQUENCE     �   ALTER TABLE public.pension ALTER COLUMN pension_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.pension_pension_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    219            �            1259    49368    reservation    TABLE     �  CREATE TABLE public.reservation (
    reserv_id integer NOT NULL,
    reserv_room_id integer NOT NULL,
    adult_guest_count integer NOT NULL,
    child_guest_count integer NOT NULL,
    reserv_guest_idno text,
    reserv_guest_name text,
    reserv_guest_mpno text,
    reserv_guest_mail text,
    reserv_total_prc integer,
    reserv_note text,
    reserv_total_days integer,
    reserv_total_guests integer,
    checkin_date date,
    checkout_date date,
    reserv_hotel_id integer
);
    DROP TABLE public.reservation;
       public         heap    postgres    false            �            1259    49413    rezervation_rezerv_id_seq    SEQUENCE     �   ALTER TABLE public.reservation ALTER COLUMN reserv_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.rezervation_rezerv_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    222            �            1259    49362    room    TABLE       CREATE TABLE public.room (
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
       public         heap    postgres    false            �            1259    49365    room_features    TABLE     �   CREATE TABLE public.room_features (
    room_feature_id integer NOT NULL,
    room_feature_room_id integer NOT NULL,
    feature_name text,
    feature_value text
);
 !   DROP TABLE public.room_features;
       public         heap    postgres    false            �            1259    49407    room_props_room_props_id_seq    SEQUENCE     �   ALTER TABLE public.room_features ALTER COLUMN room_feature_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.room_props_room_props_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    221            �            1259    49401    room_room_id_seq    SEQUENCE     �   ALTER TABLE public.room ALTER COLUMN room_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.room_room_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    220            �            1259    49356    season    TABLE     �   CREATE TABLE public.season (
    season_id integer NOT NULL,
    season_hotel_id integer NOT NULL,
    season_start date NOT NULL,
    season_end date NOT NULL,
    season_name text
);
    DROP TABLE public.season;
       public         heap    postgres    false            �            1259    49389    season_season_id_seq    SEQUENCE     �   ALTER TABLE public.season ALTER COLUMN season_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.season_season_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    218            �            1259    49347    user    TABLE     �   CREATE TABLE public."user" (
    user_id integer NOT NULL,
    user_role text NOT NULL,
    user_name text,
    user_pw text NOT NULL
);
    DROP TABLE public."user";
       public         heap    postgres    false            �            1259    49371    user_user_id_seq    SEQUENCE     �   ALTER TABLE public."user" ALTER COLUMN user_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.user_user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    215            �          0    49350    hotel 
   TABLE DATA           �   COPY public.hotel (hotel_id, hotel_city, hotel_region, hotel_name, hotel_phno, hotel_mail, hotel_star, hotel_address) FROM stdin;
    public          postgres    false    216   �3       �          0    49353    hotel_features 
   TABLE DATA           d   COPY public.hotel_features (hotel_features_id, hotel_features_hotel_id, hotel_features) FROM stdin;
    public          postgres    false    217   �4       �          0    49359    pension 
   TABLE DATA           N   COPY public.pension (pension_id, pension_hotel_id, pension_types) FROM stdin;
    public          postgres    false    219   5       �          0    49368    reservation 
   TABLE DATA           '  COPY public.reservation (reserv_id, reserv_room_id, adult_guest_count, child_guest_count, reserv_guest_idno, reserv_guest_name, reserv_guest_mpno, reserv_guest_mail, reserv_total_prc, reserv_note, reserv_total_days, reserv_total_guests, checkin_date, checkout_date, reserv_hotel_id) FROM stdin;
    public          postgres    false    222   M5       �          0    49362    room 
   TABLE DATA           �   COPY public.room (room_id, room_hotel_id, room_season_id, room_pension_id, room_stock, prc_for_child, prc_for_adult, room_type) FROM stdin;
    public          postgres    false    220   �5       �          0    49365    room_features 
   TABLE DATA           k   COPY public.room_features (room_feature_id, room_feature_room_id, feature_name, feature_value) FROM stdin;
    public          postgres    false    221   �5       �          0    49356    season 
   TABLE DATA           c   COPY public.season (season_id, season_hotel_id, season_start, season_end, season_name) FROM stdin;
    public          postgres    false    218   �6       �          0    49347    user 
   TABLE DATA           H   COPY public."user" (user_id, user_role, user_name, user_pw) FROM stdin;
    public          postgres    false    215   .7       �           0    0    hotel_hotel_id_seq    SEQUENCE SET     A   SELECT pg_catalog.setval('public.hotel_hotel_id_seq', 19, true);
          public          postgres    false    224            �           0    0    hotel_props_hotel_props_id_seq    SEQUENCE SET     M   SELECT pg_catalog.setval('public.hotel_props_hotel_props_id_seq', 51, true);
          public          postgres    false    225            �           0    0    pension_pension_id_seq    SEQUENCE SET     E   SELECT pg_catalog.setval('public.pension_pension_id_seq', 41, true);
          public          postgres    false    227            �           0    0    rezervation_rezerv_id_seq    SEQUENCE SET     G   SELECT pg_catalog.setval('public.rezervation_rezerv_id_seq', 5, true);
          public          postgres    false    230            �           0    0    room_props_room_props_id_seq    SEQUENCE SET     K   SELECT pg_catalog.setval('public.room_props_room_props_id_seq', 77, true);
          public          postgres    false    229            �           0    0    room_room_id_seq    SEQUENCE SET     ?   SELECT pg_catalog.setval('public.room_room_id_seq', 17, true);
          public          postgres    false    228            �           0    0    season_season_id_seq    SEQUENCE SET     C   SELECT pg_catalog.setval('public.season_season_id_seq', 12, true);
          public          postgres    false    226            �           0    0    user_user_id_seq    SEQUENCE SET     >   SELECT pg_catalog.setval('public.user_user_id_seq', 2, true);
          public          postgres    false    223            B           2606    74005 "   hotel_features hotel_features_pkey 
   CONSTRAINT     o   ALTER TABLE ONLY public.hotel_features
    ADD CONSTRAINT hotel_features_pkey PRIMARY KEY (hotel_features_id);
 L   ALTER TABLE ONLY public.hotel_features DROP CONSTRAINT hotel_features_pkey;
       public            postgres    false    217            @           2606    57621    hotel hotel_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.hotel
    ADD CONSTRAINT hotel_pkey PRIMARY KEY (hotel_id);
 :   ALTER TABLE ONLY public.hotel DROP CONSTRAINT hotel_pkey;
       public            postgres    false    216            F           2606    74007    pension pension_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public.pension
    ADD CONSTRAINT pension_pkey PRIMARY KEY (pension_id);
 >   ALTER TABLE ONLY public.pension DROP CONSTRAINT pension_pkey;
       public            postgres    false    219            L           2606    90389    reservation rezervation_pkey 
   CONSTRAINT     a   ALTER TABLE ONLY public.reservation
    ADD CONSTRAINT rezervation_pkey PRIMARY KEY (reserv_id);
 F   ALTER TABLE ONLY public.reservation DROP CONSTRAINT rezervation_pkey;
       public            postgres    false    222            J           2606    74013     room_features room_features_pkey 
   CONSTRAINT     k   ALTER TABLE ONLY public.room_features
    ADD CONSTRAINT room_features_pkey PRIMARY KEY (room_feature_id);
 J   ALTER TABLE ONLY public.room_features DROP CONSTRAINT room_features_pkey;
       public            postgres    false    221            H           2606    74011    room room_pkey 
   CONSTRAINT     Q   ALTER TABLE ONLY public.room
    ADD CONSTRAINT room_pkey PRIMARY KEY (room_id);
 8   ALTER TABLE ONLY public.room DROP CONSTRAINT room_pkey;
       public            postgres    false    220            D           2606    74009    season season_pkey 
   CONSTRAINT     W   ALTER TABLE ONLY public.season
    ADD CONSTRAINT season_pkey PRIMARY KEY (season_id);
 <   ALTER TABLE ONLY public.season DROP CONSTRAINT season_pkey;
       public            postgres    false    218            >           2606    49421    user user_pkey 
   CONSTRAINT     S   ALTER TABLE ONLY public."user"
    ADD CONSTRAINT user_pkey PRIMARY KEY (user_id);
 :   ALTER TABLE ONLY public."user" DROP CONSTRAINT user_pkey;
       public            postgres    false    215            �   �   x�eO;�0��S� 5i�o1����bHBC#����`��{A+@HH���g��fE [�,߄��-��GMM�\q�yw
�t!��"�t0�8���{z�f�oe�߹#��=s�2��h��(5tl���2>/�A�TR��`�TlK۬rMmK�b8Q�=w/D�c�-8�j�h.>�ܒ�����M�ǂm���'%!^o      �   >   x�31�4��t�,�K-.VHN�+I-�25�4�<<'�(��8�J�<3-���(������ ��      �   &   x�36�4��I�U(H�+ά���21�4D����� ���      �   V   x�3�44�4�4�41C�#�Rs��9M�LL-LM�M93�� ��Ԁ3%-�8�3����LtuL�LCNC�=... lU�      �   ;   x�34�4�44�41�4�420�4b�P?O����P�W.Cs�
cNSNC��L� �Q�      �   �   x���A�0D�p
��1����ҭ1�hL���DiR`�����I����df�+�ǘm�:Xڲȋ`�0�3�vf�8{Qx�EH���u�tYWY]�4�� m�3� w�A&f����Tb
��$�hd���I���6R�K �����]�g����L�ťM[iޤ�窩��_s=�<�k'��0S�;p2u�	!��'#<�xG��$�4���H� "�+N������5      �   3   x���4��4202�5 "3df���G�sq�DM��0����!TA� $'_      �   5   x�3�LL�����,J��442�2�L�-�ɯLM�<��*��<NS3�=... F��     