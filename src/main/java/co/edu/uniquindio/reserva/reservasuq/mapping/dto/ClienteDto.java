package co.edu.uniquindio.reserva.reservasuq.mapping.dto;

import java.util.List;

public record ClienteDto(
        String nombreCliente,
        String apellido,
        List<CuentaDto> cuentaDtoList
) {
}
