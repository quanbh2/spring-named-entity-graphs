# spring-named-entity-graphs
Spring Data JPA and Named Entity Graphs

https://www.baeldung.com/spring-data-jpa-named-entity-graphs
https://www.baeldung.com/spring-open-session-in-view#2-welcome-to-the-real-world

# @OneToMany & @ManyToOne

0. Item 1 - n Characteristic

1. Default FetchType
OneToMany: LAZY
ManyToOne: EAGER
ManyToMany: LAZY
OneToOne: EAGER

=> FetchType quy định thời điểm fetch data ( không quy định cách fetch data bằng 1 hay nhiều query )

2. LAZY

vd1: Không có Session ( Không nằm trong Controller, hoặc không có @Transactional)
=> Item item2 = itemRepository.findById(2L).get();
=> Query:
SELECT
	item0_.id AS id1_1_0_,
	item0_.NAME AS name2_1_0_ 
FROM
	item item0_ 
WHERE
	item0_.id =? 
=> chỉ fetch data của Item, không fetch data của Characteristic

vd2: Cách để fetch data cua Characteristic khi cần:

Cách 1: Đặt trong session ( đặt trong Controller, hoặc có @Transactional)
=> chỉ fetch data khi gọi đến : item.getCharacteristics()

Cách 2: Dùng @EntityGraph(attributePaths = { "characteristics" })
=> Item item1 = itemRepository.findByName("pepsi");
=> Query:
SELECT
	item0_.id AS id1_1_0_,
	characteri1_.id AS id1_0_1_,
	item0_.NAME AS name2_1_0_,
	characteri1_.item_id AS item_id3_0_1_,
	characteri1_.type AS type2_0_1_,
	characteri1_.item_id AS item_id3_0_0__,
	characteri1_.id AS id1_0_0__ 
FROM
	item item0_
	LEFT OUTER JOIN characteristic characteri1_ ON item0_.id = characteri1_.item_id 
WHERE
	item0_.NAME = 'pepsi'

=> fetch data cua Characteristic bằng 1 câu lệnh join
=> @EntityGraph không tác dụng với các câu query Override trong Repository

3. EAGER

=> Item item2 = itemRepository.findById(2L).get();
=> luôn fetch data cua Characteristic bằng N+1 câu query. 

4. mappedBy
=> cần có mappedBy để JPA không tạo thêm bảng quan hệ (ITEM_CHARACTERISTICS)
=> tự động tạo bảng thêm này sẽ khiến nhiều query sai !!!

@OneToMany(mappedBy = "item")
 private List<Characteristic> characteristics = new ArrayList<>();

Với mappedBy, bạn trực tiếp cho Hibernate/JPA biết rằng một bảng sở hữu mối quan hệ và do đó nó được lưu trữ dưới dạng cột của bảng đó.

Nếu không có, mối quan hệ là bên ngoài và Hibernate/JPA cần phải tạo một bảng khác để lưu trữ mối quan hệ.

=> Vidu quey sai khi call: Item item1 = itemRepository.findByName("pepsi");
SELECT
	item0_.id AS id1_1_0_,
	characteri2_.id AS id1_0_1_,
	item0_.NAME AS name2_1_0_,
	characteri2_.item_id AS item_id3_0_1_,
	characteri2_.type AS type2_0_1_,
	characteri1_.item_id AS item_id1_2_0__,
	characteri1_.characteristics_id AS characte2_2_0__ 
FROM
	item item0_
	LEFT OUTER JOIN item_characteristics characteri1_ ON item0_.id = characteri1_.item_id
	LEFT OUTER JOIN characteristic characteri2_ ON characteri1_.characteristics_id = characteri2_.id 
WHERE
	item0_.NAME =?
	
=> Do bảng 	ITEM_CHARACTERISTICS không có data