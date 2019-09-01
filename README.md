
NOMBRE: MAURICIO SALAZAR RUT: 170992210
Compilar y ejecutar el proyecto
Para copilar el proyecto se requiere Java y Maven instalado. Ingresar al directorio ApiFechasFaltantes ejecutar el siguiente comando maven

mvn package
Luego de compilar el proyecto ingresar al directorio target ejecutar el siguiente comando java

java -jar ApiFechasFaltantes-0.0.1-SNAPSHOT.jar
Nota: Debe estar disponible el puerto 8083 en el PC donde se ejecute esta API, de lo contrario, se puede camiar el puerto en el
archivo application.properties del proyecto

como pre requisito, tiene que estar el servicio GDD corriendo para ser consumido

para escritura de log en disco: ocupe el dico D: sino, omitir escirtura en disco, en archivo log4j.properties borrar lineas 11 a 16

se envia ademas proyecto postman para consumo y prueba del servicio, ademas del json de salida.
