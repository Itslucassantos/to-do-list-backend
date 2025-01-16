![Java](https://img.shields.io/static/v1?label=Java&message=17&color=blue)
![Maven](https://img.shields.io/static/v1?label=Maven&message=1.5.12&color=blue)
![SpringBoot](https://img.shields.io/static/v1?label=Spring&message=3.3.7&color=blue)
---
## Tecnologias

1. JAVA
2. MAVEN
3. SPRING BOOT

---
## Subindo a aplicação 
1. Abra o CMD nesta pasta e execute o seguinte comando para rodar em um container e subir a api:
```bash
 docker-compose up --build
```

## Caso queira buildar o projeto
1. Rode:
```bash
 mvn clean install
```

2. Abra o CMD nesta pasta e execute o seguinte comando para criar uma imagem docker e subir o container:
```bash
 docker build -t task-list .

 docker run -i -p 8082:8082 -t task-list task-list
```

## Caso queira subir a api pela IDE
1. Rode a classe StefaniniApplication

# Documentação da API
1. Quando a API estiver rodando, a documentação da API vai estar nesta url: 
http://localhost:8082/swagger-ui/index.html
