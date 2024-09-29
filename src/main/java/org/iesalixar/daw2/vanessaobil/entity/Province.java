package org.iesalixar.daw2.vanessaobil.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * La clase `Region` representa una entidad que modela una región dentro de la base de datos.
 * Contiene tres campos: `id`, `code` y `name`, donde `id` es el identificador único de la región,
 * `code` es un código asociado a la región, y `name` es el nombre de la región.
 *
 * Las anotaciones de Lombok ayudan a reducir el código repetitivo al generar automáticamente
 * métodos comunes como getters, setters, constructores, y otros métodos estándar de los objetos.
 */
@Data  // Esta anotación de Lombok genera automáticamente los siguientes métodos:
// - Getters y setters para todos los campos (id, code, name).
// - Los métodos `equals()` y `hashCode()` basados en todos los campos no transitorios.
// - El método `toString()` que incluye todos los campos.
// - Un método `canEqual()` que verifica si una instancia puede ser igual a otra.
// Esto evita tener que escribir manualmente todos estos métodos y mejora la mantenibilidad del código.


@NoArgsConstructor  // Esta anotación genera un constructor sin argumentos (constructor vacío),
//  es útil cuando quieres crear un objeto `Region` sin inicializarlo inmediatamente
// con valores. Esto es muy útil en frameworks como Hibernate o JPA,
// que requieren un constructor vacío para la creación de entidades.


//@AllArgsConstructor  // Esta anotación genera un constructor que acepta todos los campos como parámetros (id, code, name).
// Este constructor es útil cuando necesitas crear una instancia completamente inicializada de `Region`.
// Ejemplo: new Region(1, "01", "Andalucía");


public class Province {


    // Campo que almacena el identificador único de la región. Este campo suele ser autogenerado
    // por la base de datos, lo que lo convierte en un buen candidato para una clave primaria.
    private int id;


    // Campo que almacena el código de la región, normalmente una cadena corta que identifica la región.
    // Ejemplo: "01" para Andalucía.
    private String code;


    // Campo que almacena el nombre completo de la región, como "Andalucía" o "Cataluña".
    private String name;

    private Region region;


    /**
     * Este es un constructor personalizado que no incluye el campo `id`.
     * Se utiliza para crear instancias de `Province` cuando no es necesario o no se conoce el `id` de la provincia
     * (por ejemplo, antes de insertar la región en la base de datos, donde el `id` es autogenerado).
     * @param code Código de la provincia.
     * @param name Nombre de la provincia.
     */
    public Province(String code, String name) {
        this.code = code;
        this.name = name;
    }

    /**
     * Este es un constructor personalizado que incluye todos lños atributos de Province.
     * Se utiliza para crear instancias de 'Province' en donde es necesario conocer todos los valores
     * de los atributos.
     * (Por ejemplo, cuando se inserta una nueva provincia en la bsae de datos)
     * @param id id de la provincia.
     * @param code Código de la provincia.
     * @param name Nombre de la provincia.
     * @param region Región a la que pertenece la provincia.
     */
    public Province(int id, String code, String name, Region region) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.region = region;
    }

    /**
     * Este es un constructor personalizado que no incluye el compo 'id' de Province pero sí incluye
     * el campo 'region'.
     * Se utiliza para edaitar una provincia de la base de datos y ya se conoce el 'id' de la provincia
     * @param code Código de la provincia.
     * @param name Nombre de la provincia.
     * @param region Región a la que pertenece la provincia.
     */
    public Province(String code, String name, Region region) {
        this.code = code;
        this.name = name;
        this.region = region;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public int getRegionId() {
        return  region.getId();
    }

}
