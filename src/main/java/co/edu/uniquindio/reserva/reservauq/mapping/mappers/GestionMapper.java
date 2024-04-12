package co.edu.uniquindio.reserva.reservauq.mapping.mappers;
import co.edu.uniquindio.reserva.reservauq.mapping.dto.EmpleadoDto;
import co.edu.uniquindio.reserva.reservauq.mapping.dto.UsuarioDto;
import co.edu.uniquindio.reserva.reservauq.model.Empleado;
import co.edu.uniquindio.reserva.reservauq.model.Usuario;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface GestionMapper {
    GestionMapper INSTANCE = Mappers.getMapper(GestionMapper.class);

    @Named("empleadoToEmpleadoDto")
    EmpleadoDto empleadoToEmpleadoDto(Empleado empleado);

    Empleado empleadoDtoToEmpleado(EmpleadoDto empleadoDto);

    @IterableMapping(qualifiedByName = "empleadoToEmpleadoDto")
    List<EmpleadoDto> getEmpleadosDto(List<Empleado> listaEmpleados);

    @Named("usuarioToUsuarioDto")
    UsuarioDto usuarioToUsuarioDto(Usuario usuario);

    Usuario usuarioDtoToUsuario(UsuarioDto usuarioDto);

    @IterableMapping(qualifiedByName = "usuarioToUsuarioDto")
    List<UsuarioDto> getUsuariosDto(List<Usuario> listaUsuarios);
}
