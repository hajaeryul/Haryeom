import { ReactNode } from 'react';
import styled from 'styled-components';

interface StyledProps {
    top?: string;
    left?: string;
}

interface DropdownnProps extends StyledProps {
    children: ReactNode;
    open: boolean;
    closeDropdown: () => void;
}

const Dropdown = ({ children, ...props }: DropdownnProps) => {
    const { open, closeDropdown, ...styleProps } = props;

    return (
        <StyledDropDown open={open}>
            <DropdownWrapper {...styleProps}>{children}</DropdownWrapper>
            <DropdownBackground onClick={closeDropdown} />
        </StyledDropDown>
    );
};

const StyledDropDown = styled.div<{ open: boolean }>`
    ${({ open }) => !open && `display:none;`}
`;

const DropdownWrapper = styled.div<StyledProps>`
    position: absolute;
    top: ${({ top }) => (top ? top : '2em')};
    left: ${({ left }) => (left ? left : '50%')};
    ${({ left }) => !left && 'transform: translate(-50%, 0)'};
    width: min-content;
    height: min-content;
    z-index: 100;
`;

const DropdownBackground = styled.div`
    position: fixed;
    top: 0;
    left: 0;
    width: 100vw;
    height: 100vh;
`;

export default Dropdown;
